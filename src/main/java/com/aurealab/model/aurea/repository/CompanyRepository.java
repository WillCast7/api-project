package com.aurealab.model.aurea.repository;

import com.aurealab.model.aurea.entity.CompaniesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<CompaniesEntity, Long> {
}
