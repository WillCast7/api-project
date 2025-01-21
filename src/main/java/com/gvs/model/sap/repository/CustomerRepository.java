package com.gvs.model.sap.repository;

import com.gvs.model.sap.entity.AdvisorListEntity;
import com.gvs.model.sap.entity.CustomerTableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface CustomerRepository extends JpaRepository<CustomerTableEntity, Long> {

    @Query(
            value="select ocrd.CardName, " +
                "ocrd.CardCode, " +
                "ISNULL((SELECT SUM(DocTotal) FROM OINV WHERE YEAR(DocDate) = YEAR(GETDATE())-0 and CardCode = ocrd.cardcode), 0) as actual, " +
                "ISNULL((SELECT SUM(DocTotal) FROM OINV WHERE YEAR(DocDate) = YEAR(GETDATE())-1 and CardCode = ocrd.cardcode), 0) as anterior1, " +
                "ISNULL((SELECT SUM(DocTotal) FROM OINV WHERE YEAR(DocDate) = YEAR(GETDATE())-2 and CardCode = ocrd.cardcode), 0) as anterior2, " +
                "ISNULL((SELECT Perfil_Cliente.Completado FROM CRM.dbo.Perfil_Cliente WHERE Perfil_Cliente.CardCode COLLATE SQL_Latin1_General_CP850_CI_AS = ocrd.CardCode COLLATE SQL_Latin1_General_CP850_CI_AS), 0) AS completado, " +
                "ocrd.City, " +
                "OSLP.SlpName " +
                "from ocrd " +
                "inner join ocrg " +
                "on ocrg.groupcode = ocrd.groupcode " +
                "inner join OSLP " +
                "on OSLP.SlpCode = OCRD.SlpCode " +
                "ORDER BY " +
                "ocrd.CardCode " +
                "OFFSET (:page - 1) * :rows ROWS " +
                "FETCH NEXT :rows ROWS ONLY ",
            nativeQuery = true
    )
    Set<CustomerTableEntity> findList(@Param("page") Integer page, @Param("rows") Integer rows);

    @Query(value = "SELECT COUNT(*) FROM ocrd", nativeQuery = true)
    long countAllRecords();

    @Query(
            value="select ocrd.CardName, " +
                    "ocrd.CardCode, " +
                    "ISNULL((SELECT SUM(DocTotal) FROM OINV WHERE YEAR(DocDate) = YEAR(GETDATE())-0 and CardCode = ocrd.cardcode), 0) as actual, " +
                    "ISNULL((SELECT SUM(DocTotal) FROM OINV WHERE YEAR(DocDate) = YEAR(GETDATE())-1 and CardCode = ocrd.cardcode), 0) as anterior1, " +
                    "ISNULL((SELECT SUM(DocTotal) FROM OINV WHERE YEAR(DocDate) = YEAR(GETDATE())-2 and CardCode = ocrd.cardcode), 0) as anterior2, " +
                    "ISNULL((SELECT Perfil_Cliente.Completado FROM CRM.dbo.Perfil_Cliente WHERE Perfil_Cliente.CardCode COLLATE SQL_Latin1_General_CP850_CI_AS = ocrd.CardCode COLLATE SQL_Latin1_General_CP850_CI_AS), NULL) AS completado, " +
                    "ocrd.City, " +
                    "OSLP.SlpName " +
                    "from ocrd " +
                    "inner join ocrg " +
                    "on ocrg.groupcode = ocrd.groupcode " +
                    "inner join OSLP " +
                    "on OSLP.SlpCode = OCRD.SlpCode " +
                    "WHERE ocrd.CardName LIKE CONCAT('%', :searchValue, '%') OR " +
                    "ocrd.CardCode LIKE CONCAT('%', :searchValue, '%') OR " +
                    "ocrd.City LIKE CONCAT('%', :searchValue, '%') OR " +
                    "OSLP.SlpName LIKE CONCAT('%', :searchValue, '%') " +
                    "ORDER BY " +
                    "ocrd.CardCode " +
                    "OFFSET (:page - 1) * :rows ROWS " +
                    "FETCH NEXT :rows ROWS ONLY ",
            nativeQuery = true
    )
    Set<CustomerTableEntity> findListWithValue(@Param("page") Integer page, @Param("rows") Integer rows, @Param("searchValue") String searchValue);

    @Query(value = "SELECT COUNT(*) FROM ocrd " +
        "inner join ocrg " +
                "on ocrg.groupcode = ocrd.groupcode " +
                "inner join OSLP " +
                "on OSLP.SlpCode = OCRD.SlpCode " +
            "WHERE ocrd.CardName LIKE CONCAT('%', :searchValue, '%') OR " +
            "ocrd.CardCode LIKE CONCAT('%', :searchValue, '%') OR " +
            "ocrd.City LIKE CONCAT('%', :searchValue, '%') OR " +
            "OSLP.SlpName LIKE CONCAT('%', :searchValue, '%') ", nativeQuery = true)
    long countAllRecordsWithValue(@Param("searchValue") String searchValue);
}
