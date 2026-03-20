package com.intern.donorservice.repository;

import com.intern.donorservice.domain.DonorDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DonorDetailsRepo extends JpaRepository<DonorDetails , Long> {
    Optional<DonorDetails> findByUserId(Long userId);
}
