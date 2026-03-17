package com.example.DonorMicroservices.repository;

import com.example.DonorMicroservices.entity.Donor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonorRepo extends JpaRepository<Donor,Long> {
}
