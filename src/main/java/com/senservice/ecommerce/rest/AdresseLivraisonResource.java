package com.senservice.ecommerce.rest;

import com.senservice.ecommerce.model.AdresseLivraisonDTO;
import com.senservice.ecommerce.service.AdresseLivraisonService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/adresseLivraisons", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdresseLivraisonResource {

    private final AdresseLivraisonService adresseLivraisonService;

    public AdresseLivraisonResource(final AdresseLivraisonService adresseLivraisonService) {
        this.adresseLivraisonService = adresseLivraisonService;
    }

    @GetMapping
    public ResponseEntity<List<AdresseLivraisonDTO>> getAllAdresseLivraisons() {
        return ResponseEntity.ok(adresseLivraisonService.findAll());
    }

    @GetMapping("/{adresseId}")
    public ResponseEntity<AdresseLivraisonDTO> getAdresseLivraison(
            @PathVariable(name = "adresseId") final Long adresseId) {
        return ResponseEntity.ok(adresseLivraisonService.get(adresseId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createAdresseLivraison(
            @RequestBody @Valid final AdresseLivraisonDTO adresseLivraisonDTO) {
        final Long createdAdresseId = adresseLivraisonService.create(adresseLivraisonDTO);
        return new ResponseEntity<>(createdAdresseId, HttpStatus.CREATED);
    }

    @PutMapping("/{adresseId}")
    public ResponseEntity<Long> updateAdresseLivraison(
            @PathVariable(name = "adresseId") final Long adresseId,
            @RequestBody @Valid final AdresseLivraisonDTO adresseLivraisonDTO) {
        adresseLivraisonService.update(adresseId, adresseLivraisonDTO);
        return ResponseEntity.ok(adresseId);
    }

    @DeleteMapping("/{adresseId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteAdresseLivraison(
            @PathVariable(name = "adresseId") final Long adresseId) {
        adresseLivraisonService.delete(adresseId);
        return ResponseEntity.noContent().build();
    }

}
