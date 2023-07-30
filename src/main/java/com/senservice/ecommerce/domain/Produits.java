package com.senservice.ecommerce.domain;

import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Produits {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long produitId;

    @Column(nullable = false)
    private String nomProduit;

    @Column
    private String descriptionProduit;

    @Column(nullable = false)
    private String prixProduit;

    @Column
    private Integer quantityStock;

    @ManyToOne
    private Utilisateurs utilisateur;

    @ManyToOne
    private  Categorie categorie;


    @Column
    private String categorieProduit;

    @OneToMany(mappedBy = "produit")
    private Set<DetailsCommande> detailsCommandes;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}
