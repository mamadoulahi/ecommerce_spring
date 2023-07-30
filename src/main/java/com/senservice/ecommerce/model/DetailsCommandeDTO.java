package com.senservice.ecommerce.model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DetailsCommandeDTO {

    private Long detailsCommandeId;
    private Integer quantiteCommandee;
    private Double prixUnitaireCommande;
    private Long commande;
    private Long produit;

}
