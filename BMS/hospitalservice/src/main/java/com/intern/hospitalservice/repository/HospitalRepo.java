package com.intern.hospitalservice.repository;

import com.intern.hospitalservice.domain.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalRepo extends JpaRepository<Hospital , Long> {

}
