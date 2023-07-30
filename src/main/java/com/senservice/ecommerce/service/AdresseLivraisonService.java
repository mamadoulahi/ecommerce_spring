package com.senservice.ecommerce.service;

import com.senservice.ecommerce.domain.AdresseLivraison;
import com.senservice.ecommerce.domain.Utilisateurs;
import com.senservice.ecommerce.model.AdresseLivraisonDTO;
import com.senservice.ecommerce.repos.AdresseLivraisonRepository;
import com.senservice.ecommerce.repos.UtilisateursRepository;
import com.senservice.ecommerce.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class AdresseLivraisonService {

    private final AdresseLivraisonRepository adresseLivraisonRepository;
    private final UtilisateursRepository utilisateursRepository;

    public AdresseLivraisonService(final AdresseLivraisonRepository adresseLivraisonRepository,
            final UtilisateursRepository utilisateursRepository) {
        this.adresseLivraisonRepository = adresseLivraisonRepository;
        this.utilisateursRepository = utilisateursRepository;
    }

    public List<AdresseLivraisonDTO> findAll() {
        final List<AdresseLivraison> adresseLivraisons = adresseLivraisonRepository.findAll(Sort.by("adresseId"));
        return adresseLivraisons.stream()
                .map(adresseLivraison -> mapToDTO(adresseLivraison, new AdresseLivraisonDTO()))
                .toList();
    }

    public AdresseLivraisonDTO get(final Long adresseId) {
        return adresseLivraisonRepository.findById(adresseId)
                .map(adresseLivraison -> mapToDTO(adresseLivraison, new AdresseLivraisonDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final AdresseLivraisonDTO adresseLivraisonDTO) {
        final AdresseLivraison adresseLivraison = new AdresseLivraison();
        mapToEntity(adresseLivraisonDTO, adresseLivraison);
        return adresseLivraisonRepository.save(adresseLivraison).getAdresseId();
    }

    public void update(final Long adresseId, final AdresseLivraisonDTO adresseLivraisonDTO) {
        final AdresseLivraison adresseLivraison = adresseLivraisonRepository.findById(adresseId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(adresseLivraisonDTO, adresseLivraison);
        adresseLivraisonRepository.save(adresseLivraison);
    }

    public void delete(final Long adresseId) {
        adresseLivraisonRepository.deleteById(adresseId);
    }

    private AdresseLivraisonDTO mapToDTO(final AdresseLivraison adresseLivraison,
            final AdresseLivraisonDTO adresseLivraisonDTO) {
        adresseLivraisonDTO.setAdresseId(adresseLivraison.getAdresseId());
        adresseLivraisonDTO.setAdresse(adresseLivraison.getAdresse());
        adresseLivraisonDTO.setVille(adresseLivraison.getVille());
        adresseLivraisonDTO.setCodePostale(adresseLivraison.getCodePostale());
        adresseLivraisonDTO.setPays(adresseLivraison.getPays());
        adresseLivraisonDTO.setUtilisateur(adresseLivraison.getUtilisateur() == null ? null : adresseLivraison.getUtilisateur().getUserId());
        return adresseLivraisonDTO;
    }

    private AdresseLivraison mapToEntity(final AdresseLivraisonDTO adresseLivraisonDTO,
            final AdresseLivraison adresseLivraison) {
        adresseLivraison.setAdresse(adresseLivraisonDTO.getAdresse());
        adresseLivraison.setVille(adresseLivraisonDTO.getVille());
        adresseLivraison.setCodePostale(adresseLivraisonDTO.getCodePostale());
        adresseLivraison.setPays(adresseLivraisonDTO.getPays());
        final Utilisateurs utilisateur = adresseLivraisonDTO.getUtilisateur() == null ? null : utilisateursRepository.findById(adresseLivraisonDTO.getUtilisateur())
                .orElseThrow(() -> new NotFoundException("utilisateur not found"));
        adresseLivraison.setUtilisateur(utilisateur);
        return adresseLivraison;
    }

}
