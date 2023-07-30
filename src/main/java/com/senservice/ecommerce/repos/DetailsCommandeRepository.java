package com.senservice.ecommerce.repos;

import com.senservice.ecommerce.domain.DetailsCommande;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DetailsCommandeRepository extends JpaRepository<DetailsCommande, Long> {
}
