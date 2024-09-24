package com.enigmacamp.majumundur.repository;

import com.enigmacamp.majumundur.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    @Query(value = "SELECT * FROM m_role WHERE name = :name", nativeQuery = true)
    Optional<Role> findByName(@Param("name") String name);
}
