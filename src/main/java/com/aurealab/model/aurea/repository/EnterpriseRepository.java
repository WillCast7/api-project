package com.aurealab.model.aurea.repository;

import com.aurealab.model.aurea.entity.EnterpriseEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

public interface EnterpriseRepository extends CrudRepository<EnterpriseEntity, Long>, ListPagingAndSortingRepository<EnterpriseEntity, Long> {
}
