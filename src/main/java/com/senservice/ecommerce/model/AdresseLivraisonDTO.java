package com.senservice.ecommerce.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AdresseLivraisonDTO {

    private Long adresseId;

    @NotNull
    @Size(max = 70)
    private String adresse;

    @NotNull
    @Size(max = 50)
    private String ville;

    @Size(max = 50)
    private String codePostale;

    @NotNull
    @Size(max = 50)
    private String pays;

    private Long utilisateur;

}
