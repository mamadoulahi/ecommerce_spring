package com.senservice.ecommerce.repos;

import com.senservice.ecommerce.domain.Utilisateurs;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UtilisateursRepository extends JpaRepository<Utilisateurs, Long> {

    boolean existsByEmailIgnoreCase(String email);
    Utilisateurs findByEmail(String email);

}
