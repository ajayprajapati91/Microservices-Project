package com.example.HospitalMicroservices.repository;

import com.example.HospitalMicroservices.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalRepo extends JpaRepository<Hospital,Long> {
}
