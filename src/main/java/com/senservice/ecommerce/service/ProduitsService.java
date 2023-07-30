package com.senservice.ecommerce.service;

import com.senservice.ecommerce.domain.Produits;
import com.senservice.ecommerce.model.ProduitsDTO;
import com.senservice.ecommerce.repos.ProduitsRepository;
import com.senservice.ecommerce.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ProduitsService {

    private final ProduitsRepository produitsRepository;

    public ProduitsService(final ProduitsRepository produitsRepository) {
        this.produitsRepository = produitsRepository;
    }

    public List<ProduitsDTO> findAll() {
        final List<Produits> produitss = produitsRepository.findAll(Sort.by("produitId"));
        return produitss.stream()
                .map(produits -> mapToDTO(produits, new ProduitsDTO()))
                .toList();
    }

    public ProduitsDTO get(final Long produitId) {
        return produitsRepository.findById(produitId)
                .map(produits -> mapToDTO(produits, new ProduitsDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ProduitsDTO produitsDTO) {
        final Produits produits = new Produits();
        mapToEntity(produitsDTO, produits);
        return produitsRepository.save(produits).getProduitId();
    }

    public void update(final Long produitId, final ProduitsDTO produitsDTO) {
        final Produits produits = produitsRepository.findById(produitId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(produitsDTO, produits);
        produitsRepository.save(produits);
    }

    public void delete(final Long produitId) {
        produitsRepository.deleteById(produitId);
    }

    private ProduitsDTO mapToDTO(final Produits produits, final ProduitsDTO produitsDTO) {
        produitsDTO.setProduitId(produits.getProduitId());
        produitsDTO.setNomProduit(produits.getNomProduit());
        produitsDTO.setDescriptionProduit(produits.getDescriptionProduit());
        produitsDTO.setPrixProduit(produits.getPrixProduit());
        produitsDTO.setQuantityStock(produits.getQuantityStock());
        produitsDTO.setCategorieProduit(produits.getCategorieProduit());
        return produitsDTO;
    }

    private Produits mapToEntity(final ProduitsDTO produitsDTO, final Produits produits) {
        produits.setNomProduit(produitsDTO.getNomProduit());
        produits.setDescriptionProduit(produitsDTO.getDescriptionProduit());
        produits.setPrixProduit(produitsDTO.getPrixProduit());
        produits.setQuantityStock(produitsDTO.getQuantityStock());
        produits.setCategorieProduit(produitsDTO.getCategorieProduit());
        return produits;
    }

}
