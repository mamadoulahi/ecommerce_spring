package com.senservice.ecommerce.repos;

import com.senservice.ecommerce.domain.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategorieRepository extends JpaRepository<Categorie, Long> {
}
