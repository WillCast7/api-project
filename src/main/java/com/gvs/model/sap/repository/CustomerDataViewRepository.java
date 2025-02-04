package com.gvs.model.sap.repository;

import com.gvs.model.sap.entity.CustomerDataViewEntity;
import com.gvs.model.sap.entity.CustomerTableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface CustomerDataViewRepository extends JpaRepository<CustomerDataViewEntity, Long> {

    @Query(
            value="SELECT ocrd.CardCode, ocrd.Phone1, ocrd.Phone2, ocrd.E_Mail, ocrd.Address, ocrd.City FROM ocrd where ocrd.CardCode = :searchValue",
            nativeQuery = true
    )
    CustomerDataViewEntity findCustomerSapData(@Param("searchValue") String searchValue);

}
