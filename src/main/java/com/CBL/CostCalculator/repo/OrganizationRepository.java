package com.CBL.CostCalculator.repo;

import com.CBL.CostCalculator.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Integer> {

    boolean existsByName(String name);

    boolean existsByRegNo(String regNo);
}
