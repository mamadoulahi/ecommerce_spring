package com.senservice.ecommerce.service;

import com.senservice.ecommerce.domain.Commande;
import com.senservice.ecommerce.domain.DetailsCommande;
import com.senservice.ecommerce.domain.Produits;
import com.senservice.ecommerce.model.DetailsCommandeDTO;
import com.senservice.ecommerce.repos.CommandeRepository;
import com.senservice.ecommerce.repos.DetailsCommandeRepository;
import com.senservice.ecommerce.repos.ProduitsRepository;
import com.senservice.ecommerce.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class DetailsCommandeService {

    private final DetailsCommandeRepository detailsCommandeRepository;
    private final CommandeRepository commandeRepository;
    private final ProduitsRepository produitsRepository;

    public DetailsCommandeService(final DetailsCommandeRepository detailsCommandeRepository,
            final CommandeRepository commandeRepository,
            final ProduitsRepository produitsRepository) {
        this.detailsCommandeRepository = detailsCommandeRepository;
        this.commandeRepository = commandeRepository;
        this.produitsRepository = produitsRepository;
    }

    public List<DetailsCommandeDTO> findAll() {
        final List<DetailsCommande> detailsCommandes = detailsCommandeRepository.findAll(Sort.by("detailsCommandeId"));
        return detailsCommandes.stream()
                .map(detailsCommande -> mapToDTO(detailsCommande, new DetailsCommandeDTO()))
                .toList();
    }

    public DetailsCommandeDTO get(final Long detailsCommandeId) {
        return detailsCommandeRepository.findById(detailsCommandeId)
                .map(detailsCommande -> mapToDTO(detailsCommande, new DetailsCommandeDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final DetailsCommandeDTO detailsCommandeDTO) {
        final DetailsCommande detailsCommande = new DetailsCommande();
        mapToEntity(detailsCommandeDTO, detailsCommande);
        return detailsCommandeRepository.save(detailsCommande).getDetailsCommandeId();
    }

    public void update(final Long detailsCommandeId, final DetailsCommandeDTO detailsCommandeDTO) {
        final DetailsCommande detailsCommande = detailsCommandeRepository.findById(detailsCommandeId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(detailsCommandeDTO, detailsCommande);
        detailsCommandeRepository.save(detailsCommande);
    }

    public void delete(final Long detailsCommandeId) {
        detailsCommandeRepository.deleteById(detailsCommandeId);
    }

    private DetailsCommandeDTO mapToDTO(final DetailsCommande detailsCommande,
            final DetailsCommandeDTO detailsCommandeDTO) {
        detailsCommandeDTO.setDetailsCommandeId(detailsCommande.getDetailsCommandeId());
        detailsCommandeDTO.setQuantiteCommandee(detailsCommande.getQuantiteCommandee());
        detailsCommandeDTO.setPrixUnitaireCommande(detailsCommande.getPrixUnitaireCommande());
        detailsCommandeDTO.setCommande(detailsCommande.getCommande() == null ? null : detailsCommande.getCommande().getCommandeId());
        detailsCommandeDTO.setProduit(detailsCommande.getProduit() == null ? null : detailsCommande.getProduit().getProduitId());
        return detailsCommandeDTO;
    }

    private DetailsCommande mapToEntity(final DetailsCommandeDTO detailsCommandeDTO,
            final DetailsCommande detailsCommande) {
        detailsCommande.setQuantiteCommandee(detailsCommandeDTO.getQuantiteCommandee());
        detailsCommande.setPrixUnitaireCommande(detailsCommandeDTO.getPrixUnitaireCommande());
        final Commande commande = detailsCommandeDTO.getCommande() == null ? null : commandeRepository.findById(detailsCommandeDTO.getCommande())
                .orElseThrow(() -> new NotFoundException("commande not found"));
        detailsCommande.setCommande(commande);
        final Produits produit = detailsCommandeDTO.getProduit() == null ? null : produitsRepository.findById(detailsCommandeDTO.getProduit())
                .orElseThrow(() -> new NotFoundException("produit not found"));
        detailsCommande.setProduit(produit);
        return detailsCommande;
    }

}
