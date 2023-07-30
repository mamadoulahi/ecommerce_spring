package com.senservice.ecommerce.rest;

import com.senservice.ecommerce.model.CategorieDTO;
import com.senservice.ecommerce.service.CategorieService;
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
@RequestMapping(value = "/api/categories", produces = MediaType.APPLICATION_JSON_VALUE)
public class CategorieResource {

    private final CategorieService categorieService;

    public CategorieResource(final CategorieService categorieService) {
        this.categorieService = categorieService;
    }

    @GetMapping
    public ResponseEntity<List<CategorieDTO>> getAllCategories() {
        return ResponseEntity.ok(categorieService.findAll());
    }

    @GetMapping("/{categorieId}")
    public ResponseEntity<CategorieDTO> getCategorie(
            @PathVariable(name = "categorieId") final Long categorieId) {
        return ResponseEntity.ok(categorieService.get(categorieId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createCategorie(
            @RequestBody @Valid final CategorieDTO categorieDTO) {
        final Long createdCategorieId = categorieService.create(categorieDTO);
        return new ResponseEntity<>(createdCategorieId, HttpStatus.CREATED);
    }

    @PutMapping("/{categorieId}")
    public ResponseEntity<Long> updateCategorie(
            @PathVariable(name = "categorieId") final Long categorieId,
            @RequestBody @Valid final CategorieDTO categorieDTO) {
        categorieService.update(categorieId, categorieDTO);
        return ResponseEntity.ok(categorieId);
    }

    @DeleteMapping("/{categorieId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteCategorie(
            @PathVariable(name = "categorieId") final Long categorieId) {
        categorieService.delete(categorieId);
        return ResponseEntity.noContent().build();
    }

}
