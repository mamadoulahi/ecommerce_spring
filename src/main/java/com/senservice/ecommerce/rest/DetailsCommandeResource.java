package com.senservice.ecommerce.rest;

import com.senservice.ecommerce.model.DetailsCommandeDTO;
import com.senservice.ecommerce.service.DetailsCommandeService;
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
@RequestMapping(value = "/api/detailsCommandes", produces = MediaType.APPLICATION_JSON_VALUE)
public class DetailsCommandeResource {

    private final DetailsCommandeService detailsCommandeService;

    public DetailsCommandeResource(final DetailsCommandeService detailsCommandeService) {
        this.detailsCommandeService = detailsCommandeService;
    }

    @GetMapping
    public ResponseEntity<List<DetailsCommandeDTO>> getAllDetailsCommandes() {
        return ResponseEntity.ok(detailsCommandeService.findAll());
    }

    @GetMapping("/{detailsCommandeId}")
    public ResponseEntity<DetailsCommandeDTO> getDetailsCommande(
            @PathVariable(name = "detailsCommandeId") final Long detailsCommandeId) {
        return ResponseEntity.ok(detailsCommandeService.get(detailsCommandeId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createDetailsCommande(
            @RequestBody @Valid final DetailsCommandeDTO detailsCommandeDTO) {
        final Long createdDetailsCommandeId = detailsCommandeService.create(detailsCommandeDTO);
        return new ResponseEntity<>(createdDetailsCommandeId, HttpStatus.CREATED);
    }

    @PutMapping("/{detailsCommandeId}")
    public ResponseEntity<Long> updateDetailsCommande(
            @PathVariable(name = "detailsCommandeId") final Long detailsCommandeId,
            @RequestBody @Valid final DetailsCommandeDTO detailsCommandeDTO) {
        detailsCommandeService.update(detailsCommandeId, detailsCommandeDTO);
        return ResponseEntity.ok(detailsCommandeId);
    }

    @DeleteMapping("/{detailsCommandeId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteDetailsCommande(
            @PathVariable(name = "detailsCommandeId") final Long detailsCommandeId) {
        detailsCommandeService.delete(detailsCommandeId);
        return ResponseEntity.noContent().build();
    }

}
