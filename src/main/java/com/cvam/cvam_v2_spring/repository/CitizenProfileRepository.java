package com.cvam.cvam_v2_spring.repository;

import com.cvam.cvam_v2_spring.model.CitizenProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CitizenProfileRepository extends JpaRepository<CitizenProfile, Long> {

    //1. Find a profile directly using the primary database ID of the User
    //Spring reads this as: CitizenProfile -> User -> Id
    Optional<CitizenProfile> findUserById(Long userId);

    //2. Find a profile using the unique Fiscal Code inside the User object
    //Spring reads this as: CitizenProfile -> User -> FiscalCode
    Optional<CitizenProfile> findUserByFiscalCode(String fiscalCode);

    //3. Advanced Optimization (Solving N+1 Performance Issues)

    /**
     * Because the OneToOne relationship uses FetchType.LAZY, calling citizenProfile.getUser()
     * later would fire a second query. This custom JPQL joins and loads them in 1 clean database trip.
     */
    @Query("SELECT c FROM CitizenProfile c JOIN FETCH c.user WHERE c.id = :id")
    Optional<CitizenProfile> findByIdWithUser(@Param("id") Long id);


}