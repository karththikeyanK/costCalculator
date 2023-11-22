package com.CBL.CostCalculator.repo;

import com.CBL.CostCalculator.entity.Region;
import com.CBL.CostCalculator.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Integer> {
    boolean existsByRegNumber(String regNumber);

    List<Shop> findAllByRegion(Region region);
}
