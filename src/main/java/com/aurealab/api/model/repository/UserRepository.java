package com.aurealab.api.model.repository;

import com.aurealab.api.model.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long>, ListPagingAndSortingRepository<UserEntity, Long> {

    @Query("SELECT u FROM UserEntity u JOIN FETCH u.role r JOIN FETCH r.perrmissionList WHERE u.userName = :username")
    Optional<UserEntity> findUserEntityByUserName(String username);
}
