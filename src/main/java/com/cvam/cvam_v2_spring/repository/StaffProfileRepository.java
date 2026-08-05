package com.cvam.cvam_v2_spring.repository;

import com.cvam.cvam_v2_spring.model.StaffProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StaffProfileRepository extends JpaRepository<StaffProfile, Long>{
}