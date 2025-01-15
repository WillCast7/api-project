package com.gvs.model.sap.repository;

import com.gvs.model.sap.entity.CustomerTableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface CustomerRepository extends JpaRepository<CustomerTableEntity, Long> {

    @Query(
            value="select ocrd.CardName, " +
                "ocrd.CardCode, " +
                "(SELECT SUM(DocTotal) FROM OINV WHERE YEAR(DocDate) = YEAR(GETDATE())-0 and CardCode = ocrd.cardcode)as actual, " +
                "(SELECT SUM(DocTotal) FROM OINV WHERE YEAR(DocDate) = YEAR(GETDATE())-1 and CardCode = ocrd.cardcode)as anterior1, " +
                "(SELECT SUM(DocTotal) FROM OINV WHERE YEAR(DocDate) = YEAR(GETDATE())-2 and CardCode = ocrd.cardcode)as anterior2, " +
                "(SELECT SUM(DocTotal) FROM OINV WHERE YEAR(DocDate) = YEAR(GETDATE())-3 and CardCode = ocrd.cardcode)as anterior3, " +
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
}
