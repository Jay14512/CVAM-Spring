package com.cvam.cvam_v2_spring.repository;

import com.cvam.cvam_v2_spring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //Spring generates: SELECT * FROM users WHERE email = ?
    Optional<User> findByEmail(String email);

    //Spring generates: SELECT * FROM users WHERE fiscal_code = ?
    Optional <User> findUserByFiscalCode (String fiscalCode);

    //Spring generates: SELECT COUNT(*) > 0 FROM users WHERE email = ?
    boolean existsByEmail(String email);

    //Spring generates: SELECT COUNT(*) > 0 FROM users WHERE fiscal_code = ?
    boolean existsByFiscalCode(String fiscalCode);

}