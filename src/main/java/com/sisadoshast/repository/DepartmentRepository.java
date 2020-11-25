package com.sisadoshast.repository;

import com.sisadoshast.domain.Department;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Department entity.
 */
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query(value = "select distinct department from Department department left join fetch department.employees",
        countQuery = "select count(distinct department) from Department department")
    Page<Department> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct department from Department department left join fetch department.employees")
    List<Department> findAllWithEagerRelationships();

    @Query("select department from Department department left join fetch department.employees where department.id =:id")
    Optional<Department> findOneWithEagerRelationships(@Param("id") Long id);

}
