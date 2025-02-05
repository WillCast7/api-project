package com.aurealab.model.aurea.repository;

import com.aurealab.model.aurea.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long>, ListPagingAndSortingRepository<UserEntity, Long> {

    @Query(value = "EXEC [dbo].[CS_Login2] :username, :password", nativeQuery = true)
    Optional<UserEntity> login(@Param("username") String username, @Param("password") String password);
}
