package com.senservice.ecommerce.model;

import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CommandeDTO {

    private Long commandeId;

    private LocalDate dateCommande;

    @Size(max = 200)
    private String statut;

    @Size(max = 255)
    private String montantTotal;

    private Long utilisateur;

}
