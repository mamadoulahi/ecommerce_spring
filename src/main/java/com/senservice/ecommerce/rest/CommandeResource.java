package com.senservice.ecommerce.rest;

import com.senservice.ecommerce.model.CommandeDTO;
import com.senservice.ecommerce.service.CommandeService;
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
@RequestMapping(value = "/api/commandes", produces = MediaType.APPLICATION_JSON_VALUE)
public class CommandeResource {

    private final CommandeService commandeService;

    public CommandeResource(final CommandeService commandeService) {
        this.commandeService = commandeService;
    }

    @GetMapping
    public ResponseEntity<List<CommandeDTO>> getAllCommandes() {
        return ResponseEntity.ok(commandeService.findAll());
    }

    @GetMapping("/{commandeId}")
    public ResponseEntity<CommandeDTO> getCommande(
            @PathVariable(name = "commandeId") final Long commandeId) {
        return ResponseEntity.ok(commandeService.get(commandeId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createCommande(@RequestBody @Valid final CommandeDTO commandeDTO) {
        final Long createdCommandeId = commandeService.create(commandeDTO);
        return new ResponseEntity<>(createdCommandeId, HttpStatus.CREATED);
    }

    @PutMapping("/{commandeId}")
    public ResponseEntity<Long> updateCommande(
            @PathVariable(name = "commandeId") final Long commandeId,
            @RequestBody @Valid final CommandeDTO commandeDTO) {
        commandeService.update(commandeId, commandeDTO);
        return ResponseEntity.ok(commandeId);
    }

    @DeleteMapping("/{commandeId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteCommande(
            @PathVariable(name = "commandeId") final Long commandeId) {
        commandeService.delete(commandeId);
        return ResponseEntity.noContent().build();
    }

}
