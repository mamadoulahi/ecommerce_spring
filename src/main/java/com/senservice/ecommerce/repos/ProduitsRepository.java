package com.senservice.ecommerce.repos;

import com.senservice.ecommerce.domain.Produits;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProduitsRepository extends JpaRepository<Produits, Long> {
}
