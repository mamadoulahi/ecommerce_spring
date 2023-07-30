package com.senservice.ecommerce.service;

import com.senservice.ecommerce.domain.Categorie;
import com.senservice.ecommerce.model.CategorieDTO;
import com.senservice.ecommerce.repos.CategorieRepository;
import com.senservice.ecommerce.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class CategorieService {

    private final CategorieRepository categorieRepository;

    public CategorieService(final CategorieRepository categorieRepository) {
        this.categorieRepository = categorieRepository;
    }

    public List<CategorieDTO> findAll() {
        final List<Categorie> categories = categorieRepository.findAll(Sort.by("categorieId"));
        return categories.stream()
                .map(categorie -> mapToDTO(categorie, new CategorieDTO()))
                .toList();
    }

    public CategorieDTO get(final Long categorieId) {
        return categorieRepository.findById(categorieId)
                .map(categorie -> mapToDTO(categorie, new CategorieDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final CategorieDTO categorieDTO) {
        final Categorie categorie = new Categorie();
        mapToEntity(categorieDTO, categorie);
        return categorieRepository.save(categorie).getCategorieId();
    }

    public void update(final Long categorieId, final CategorieDTO categorieDTO) {
        final Categorie categorie = categorieRepository.findById(categorieId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(categorieDTO, categorie);
        categorieRepository.save(categorie);
    }

    public void delete(final Long categorieId) {
        categorieRepository.deleteById(categorieId);
    }

    private CategorieDTO mapToDTO(final Categorie categorie, final CategorieDTO categorieDTO) {
        categorieDTO.setCategorieId(categorie.getCategorieId());
        categorieDTO.setNomCategorie(categorie.getNomCategorie());
        categorieDTO.setDescriptionCategorie(categorie.getDescriptionCategorie());
        return categorieDTO;
    }

    private Categorie mapToEntity(final CategorieDTO categorieDTO, final Categorie categorie) {
        categorie.setNomCategorie(categorieDTO.getNomCategorie());
        categorie.setDescriptionCategorie(categorieDTO.getDescriptionCategorie());
        return categorie;
    }

}
