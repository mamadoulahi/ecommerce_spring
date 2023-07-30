package com.senservice.ecommerce.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CategorieDTO {

    private Long categorieId;

    @NotNull
    @Size(max = 100)
    private String nomCategorie;

    @Size(max = 150)
    private String descriptionCategorie;

}
