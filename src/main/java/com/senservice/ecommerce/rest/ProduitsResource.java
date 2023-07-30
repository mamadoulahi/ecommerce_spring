package com.senservice.ecommerce.rest;

import com.senservice.ecommerce.model.ProduitsDTO;
import com.senservice.ecommerce.service.ProduitsService;
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
@RequestMapping(value = "/api/produits", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProduitsResource {

    private final ProduitsService produitsService;

    public ProduitsResource(final ProduitsService produitsService) {
        this.produitsService = produitsService;
    }

    @GetMapping
    public ResponseEntity<List<ProduitsDTO>> getAllProduitss() {
        return ResponseEntity.ok(produitsService.findAll());
    }

    @GetMapping("/{produitId}")
    public ResponseEntity<ProduitsDTO> getProduits(
            @PathVariable(name = "produitId") final Long produitId) {
        return ResponseEntity.ok(produitsService.get(produitId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createProduits( @RequestBody @Valid final ProduitsDTO produitsDTO) {
        final Long createdProduitId = produitsService.create(produitsDTO);
        return new ResponseEntity<>(createdProduitId, HttpStatus.CREATED);
    }

    @PutMapping("/{produitId}")
    public ResponseEntity<Long> updateProduits(
            @PathVariable(name = "produitId") final Long produitId,
            @RequestBody @Valid final ProduitsDTO produitsDTO) {
        produitsService.update(produitId, produitsDTO);
        return ResponseEntity.ok(produitId);
    }

    @DeleteMapping("/{produitId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteProduits(
            @PathVariable(name = "produitId") final Long produitId) {
        produitsService.delete(produitId);
        return ResponseEntity.noContent().build();
    }

}
