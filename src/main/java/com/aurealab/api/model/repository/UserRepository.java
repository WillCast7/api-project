package com.aurealab.api.model.repository;

import com.aurealab.api.model.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long>, ListPagingAndSortingRepository<UserEntity, Long> {
}
