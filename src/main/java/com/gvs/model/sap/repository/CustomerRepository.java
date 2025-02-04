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
                    "PC.Completado, " +
                    "ocrd.City, " +
                    "OSLP.SlpName " +
                "FROM ocrd " +
                "LEFT JOIN OSLP " +
                    "on OSLP.SlpCode = OCRD.SlpCode " +
                "FULL JOIN CRM.dbo.Perfil_Cliente PC " +
                    "ON PC.CardCode COLLATE SQL_Latin1_General_CP850_CI_AS = ocrd.CardCode COLLATE SQL_Latin1_General_CP850_CI_AS " +
                "WHERE ocrd.CardName LIKE CONCAT('%', :searchValue, '%') OR " +
                "ocrd.CardCode LIKE CONCAT('%', :searchValue, '%') OR " +
                "ocrd.City LIKE CONCAT('%', :searchValue, '%') OR " +
                "OSLP.SlpName LIKE CONCAT('%', :searchValue, '%') " +
                "ORDER BY ocrd.CardCode DESC " +
                "OFFSET (:page - 1) * :rows ROWS " +
                "FETCH NEXT :rows ROWS ONLY ",
            nativeQuery = true
    )
    Set<CustomerTableEntity> findList(@Param("page") Integer page, @Param("rows") Integer rows, @Param("searchValue") String searchValue);

    @Query(
        value = "SELECT COUNT(*)" +
            "FROM ocrd " +
            "INNER JOIN OSLP " +
                "on OSLP.SlpCode = OCRD.SlpCode " +
            "FULL JOIN CRM.dbo.Perfil_Cliente PC " +
                "ON PC.CardCode COLLATE SQL_Latin1_General_CP850_CI_AS = ocrd.CardCode COLLATE SQL_Latin1_General_CP850_CI_AS " +
            "WHERE ocrd.CardName LIKE CONCAT('%', :searchValue, '%') OR " +
            "ocrd.CardCode LIKE CONCAT('%', :searchValue, '%') OR " +
            "ocrd.City LIKE CONCAT('%', :searchValue, '%') OR " +
            "OSLP.SlpName LIKE CONCAT('%', :searchValue, '%') ",
        nativeQuery = true)
    long countAllRecords(@Param("searchValue") String searchValue);

    @Query(
            value="SELECT ocrd.CardName, " +
                    "ocrd.CardCode, " +
                    "ISNULL((SELECT SUM(DocTotal) FROM OINV WHERE YEAR(DocDate) = YEAR(GETDATE())-0 and CardCode = ocrd.cardcode), 0) as actual, " +
                    "ISNULL((SELECT SUM(DocTotal) FROM OINV WHERE YEAR(DocDate) = YEAR(GETDATE())-1 and CardCode = ocrd.cardcode), 0) as anterior1, " +
                    "ISNULL((SELECT SUM(DocTotal) FROM OINV WHERE YEAR(DocDate) = YEAR(GETDATE())-2 and CardCode = ocrd.cardcode), 0) as anterior2, " +
                    "PC.Completado, " +
                    "ocrd.City, " +
                    "OSLP.SlpName " +
                    "FROM ocrd " +
                    "LEFT JOIN OSLP " +
                    "on OSLP.SlpCode = OCRD.SlpCode " +
                    "INNER JOIN CRM.dbo.Perfil_Cliente PC " +
                    "ON PC.CardCode COLLATE SQL_Latin1_General_CP850_CI_AS = ocrd.CardCode COLLATE SQL_Latin1_General_CP850_CI_AS " +
                    "WHERE (PC.Completado = 1) AND (ocrd.CardName LIKE CONCAT('%', :searchValue, '%') OR " +
                    "ocrd.CardCode LIKE CONCAT('%', :searchValue, '%') OR " +
                    "ocrd.City LIKE CONCAT('%', :searchValue, '%') OR " +
                    "OSLP.SlpName LIKE CONCAT('%', :searchValue, '%')) " +
                    "ORDER BY " +
                    "ocrd.CardCode " +
                    "OFFSET (:page - 1) * :rows ROWS " +
                    "FETCH NEXT :rows ROWS ONLY ",
            nativeQuery = true)
    Set<CustomerTableEntity> findListCompleted(@Param("page") Integer page, @Param("rows") Integer rows, @Param("searchValue") String searchValue);

    @Query(
            value = "SELECT COUNT(*)" +
                    "FROM ocrd " +
                    "inner join OSLP " +
                    "ON OSLP.SlpCode = OCRD.SlpCode " +
                    "INNER JOIN CRM.dbo.Perfil_Cliente PC " +
                    "ON PC.CardCode COLLATE SQL_Latin1_General_CP850_CI_AS = ocrd.CardCode COLLATE SQL_Latin1_General_CP850_CI_AS " +
                    "WHERE (PC.Completado = 1) AND (ocrd.CardName LIKE CONCAT('%', :searchValue, '%') OR " +
                    "ocrd.CardCode LIKE CONCAT('%', :searchValue, '%') OR " +
                    "ocrd.City LIKE CONCAT('%', :searchValue, '%') OR " +
                    "OSLP.SlpName LIKE CONCAT('%', :searchValue, '%')) ", nativeQuery = true)
    long countAllRecordsCompleted(@Param("searchValue") String searchValue);

    @Query(
        value="SELECT ocrd.CardName, " +
                "ocrd.CardCode, " +
                "ISNULL((SELECT SUM(DocTotal) FROM OINV WHERE YEAR(DocDate) = YEAR(GETDATE())-0 and CardCode = ocrd.cardcode), 0) as actual, " +
                "ISNULL((SELECT SUM(DocTotal) FROM OINV WHERE YEAR(DocDate) = YEAR(GETDATE())-1 and CardCode = ocrd.cardcode), 0) as anterior1, " +
                "ISNULL((SELECT SUM(DocTotal) FROM OINV WHERE YEAR(DocDate) = YEAR(GETDATE())-2 and CardCode = ocrd.cardcode), 0) as anterior2, " +
                "PC.Completado, " +
                "ocrd.City, " +
                "OSLP.SlpName " +
            "FROM ocrd " +
            "LEFT JOIN OSLP " +
                "on OSLP.SlpCode = OCRD.SlpCode " +
            "INNER JOIN CRM.dbo.Perfil_Cliente PC " +
                "ON PC.CardCode COLLATE SQL_Latin1_General_CP850_CI_AS = ocrd.CardCode COLLATE SQL_Latin1_General_CP850_CI_AS " +
            "WHERE (PC.Completado = 0) AND (ocrd.CardName LIKE CONCAT('%', :searchValue, '%') OR " +
            "ocrd.CardCode LIKE CONCAT('%', :searchValue, '%') OR " +
            "ocrd.City LIKE CONCAT('%', :searchValue, '%') OR " +
            "OSLP.SlpName LIKE CONCAT('%', :searchValue, '%')) " +
            "ORDER BY " +
                "ocrd.CardCode " +
            "OFFSET (:page - 1) * :rows ROWS " +
            "FETCH NEXT :rows ROWS ONLY ",
        nativeQuery = true)
    Set<CustomerTableEntity> findListInitialized(@Param("page") Integer page, @Param("rows") Integer rows, @Param("searchValue") String searchValue);

    @Query(
        value = "SELECT COUNT(*)" +
            "FROM ocrd " +
            "inner join OSLP " +
                "ON OSLP.SlpCode = OCRD.SlpCode " +
            "INNER JOIN CRM.dbo.Perfil_Cliente PC " +
                "ON PC.CardCode COLLATE SQL_Latin1_General_CP850_CI_AS = ocrd.CardCode COLLATE SQL_Latin1_General_CP850_CI_AS " +
            "WHERE (PC.Completado = 0) AND (ocrd.CardName LIKE CONCAT('%', :searchValue, '%') OR " +
            "ocrd.CardCode LIKE CONCAT('%', :searchValue, '%') OR " +
            "ocrd.City LIKE CONCAT('%', :searchValue, '%') OR " +
            "OSLP.SlpName LIKE CONCAT('%', :searchValue, '%')) ", nativeQuery = true)
    long countAllRecordsInitialized(@Param("searchValue") String searchValue);

    @Query(
            value="select ocrd.CardName, " +
                    "ocrd.CardCode, " +
                    "ISNULL((SELECT SUM(DocTotal) FROM OINV WHERE YEAR(DocDate) = YEAR(GETDATE())-0 and CardCode = ocrd.cardcode), 0) as actual, " +
                    "ISNULL((SELECT SUM(DocTotal) FROM OINV WHERE YEAR(DocDate) = YEAR(GETDATE())-1 and CardCode = ocrd.cardcode), 0) as anterior1, " +
                    "ISNULL((SELECT SUM(DocTotal) FROM OINV WHERE YEAR(DocDate) = YEAR(GETDATE())-2 and CardCode = ocrd.cardcode), 0) as anterior2, " +
                    "PC.Completado, " +
                    "ocrd.City, " +
                    "OSLP.SlpName " +
                "FROM ocrd " +
                "LEFT JOIN OSLP " +
                    "on OSLP.SlpCode = OCRD.SlpCode " +
                "LEFT JOIN CRM.dbo.Perfil_Cliente PC " +
                    "ON PC.CardCode COLLATE SQL_Latin1_General_CP850_CI_AS = ocrd.CardCode COLLATE SQL_Latin1_General_CP850_CI_AS " +
                "WHERE (PC.Completado IS NULL) " +
                "AND (ocrd.CardName LIKE CONCAT('%', :searchValue, '%') OR " +
                "ocrd.CardCode LIKE CONCAT('%', :searchValue, '%') OR " +
                "ocrd.City LIKE CONCAT('%', :searchValue, '%') OR " +
                "OSLP.SlpName LIKE CONCAT('%', :searchValue, '%')) " +
                "ORDER BY ocrd.CardCode ASC " +
                "OFFSET (:page - 1) * :rows ROWS " +
                "FETCH NEXT :rows ROWS ONLY ",
            nativeQuery = true
    )
    Set<CustomerTableEntity> findListWithOutInit(@Param("page") Integer page, @Param("rows") Integer rows, @Param("searchValue") String searchValue);

    @Query(
            value = "SELECT COUNT(*)" +
                "FROM ocrd " +
                "INNER JOIN OSLP " +
                    "on OSLP.SlpCode = OCRD.SlpCode " +
                "LEFT JOIN CRM.dbo.Perfil_Cliente PC " +
                    "ON PC.CardCode COLLATE SQL_Latin1_General_CP850_CI_AS = ocrd.CardCode COLLATE SQL_Latin1_General_CP850_CI_AS " +
                "WHERE (PC.Completado IS NULL) " +
                "AND (ocrd.CardName LIKE CONCAT('%', :searchValue, '%') OR " +
                "ocrd.CardCode LIKE CONCAT('%', :searchValue, '%') OR " +
                "ocrd.City LIKE CONCAT('%', :searchValue, '%') OR " +
                "OSLP.SlpName LIKE CONCAT('%', :searchValue, '%')) ",
            nativeQuery = true)
    long countAllRecordsWithOutInit(@Param("searchValue") String searchValue);


}
