package com.senservice.ecommerce.service;

import com.senservice.ecommerce.domain.Commande;
import com.senservice.ecommerce.domain.Utilisateurs;
import com.senservice.ecommerce.model.CommandeDTO;
import com.senservice.ecommerce.repos.CommandeRepository;
import com.senservice.ecommerce.repos.UtilisateursRepository;
import com.senservice.ecommerce.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class CommandeService {

    private final CommandeRepository commandeRepository;
    private final UtilisateursRepository utilisateursRepository;

    public CommandeService(final CommandeRepository commandeRepository,
            final UtilisateursRepository utilisateursRepository) {
        this.commandeRepository = commandeRepository;
        this.utilisateursRepository = utilisateursRepository;
    }

    public List<CommandeDTO> findAll() {
        final List<Commande> commandes = commandeRepository.findAll(Sort.by("commandeId"));
        return commandes.stream()
                .map(commande -> mapToDTO(commande, new CommandeDTO()))
                .toList();
    }

    public CommandeDTO get(final Long commandeId) {
        return commandeRepository.findById(commandeId)
                .map(commande -> mapToDTO(commande, new CommandeDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final CommandeDTO commandeDTO) {
        final Commande commande = new Commande();
        mapToEntity(commandeDTO, commande);
        return commandeRepository.save(commande).getCommandeId();
    }

    public void update(final Long commandeId, final CommandeDTO commandeDTO) {
        final Commande commande = commandeRepository.findById(commandeId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(commandeDTO, commande);
        commandeRepository.save(commande);
    }

    public void delete(final Long commandeId) {
        commandeRepository.deleteById(commandeId);
    }

    private CommandeDTO mapToDTO(final Commande commande, final CommandeDTO commandeDTO) {
        commandeDTO.setCommandeId(commande.getCommandeId());
        commandeDTO.setDateCommande(commande.getDateCommande());
        commandeDTO.setStatut(commande.getStatut());
        commandeDTO.setMontantTotal(commande.getMontantTotal());
        commandeDTO.setUtilisateur(commande.getUtilisateur() == null ? null : commande.getUtilisateur().getUserId());
        return commandeDTO;
    }

    private Commande mapToEntity(final CommandeDTO commandeDTO, final Commande commande) {
        commande.setDateCommande(commandeDTO.getDateCommande());
        commande.setStatut(commandeDTO.getStatut());
        commande.setMontantTotal(commandeDTO.getMontantTotal());
        final Utilisateurs utilisateur = commandeDTO.getUtilisateur() == null ? null : utilisateursRepository.findById(commandeDTO.getUtilisateur())
                .orElseThrow(() -> new NotFoundException("utilisateur not found"));
        commande.setUtilisateur(utilisateur);
        return commande;
    }

}
