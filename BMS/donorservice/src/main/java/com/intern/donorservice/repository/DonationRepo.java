package com.intern.donorservice.repository;

import com.intern.donorservice.domain.Donation;
import com.intern.donorservice.domain.DonorDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DonationRepo extends JpaRepository<Donation , Long> {
    Optional<List<Donation>> findByDonorDetails(DonorDetails donorDetails);
}
