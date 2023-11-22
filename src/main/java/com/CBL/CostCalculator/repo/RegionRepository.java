package com.CBL.CostCalculator.repo;

import com.CBL.CostCalculator.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends JpaRepository<Region, Integer> {
    boolean existsByName(String name);

    boolean existsByCode(String code);
}
