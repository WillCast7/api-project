package com.gvs.model.sap.repository;

import com.gvs.model.sap.entity.AdvisorListEntity;
import com.gvs.model.sap.entity.CustomerTableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface OSLPRepository extends JpaRepository<AdvisorListEntity, Long> {

    @Query(value = "SELECT SlpCode, SlpName FROM OSLP ORDER BY SlpName ASC ", nativeQuery = true)
    Set<AdvisorListEntity> findAllAdvisors();
}
