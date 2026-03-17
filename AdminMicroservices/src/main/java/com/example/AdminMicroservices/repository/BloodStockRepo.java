package com.example.AdminMicroservices.repository;

import com.example.AdminMicroservices.entity.BloodStock;
import com.example.AdminMicroservices.enums.BloodGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BloodStockRepo extends JpaRepository<BloodStock,Long> {
    Optional<BloodStock> findByBloodGroup(BloodGroup bloodGroup);
}
