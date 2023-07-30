package com.senservice.ecommerce.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProduitsDTO {

    private Long produitId;

    @NotNull
    @Size(max = 255)
    private String nomProduit;

    @Size(max = 255)
    private String descriptionProduit;

    @NotNull
    @Size(max = 255)
    private String prixProduit;

    private Integer quantityStock;

    @Size(max = 255)
    private String categorieProduit;

}
