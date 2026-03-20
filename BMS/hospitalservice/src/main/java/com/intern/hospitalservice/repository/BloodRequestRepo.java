package com.intern.hospitalservice.repository;

import com.intern.hospitalservice.domain.BloodRequest;
import com.intern.hospitalservice.domain.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BloodRequestRepo extends JpaRepository<BloodRequest , Long> {
    List<BloodRequest> findByHospital(Hospital hospital);
}
