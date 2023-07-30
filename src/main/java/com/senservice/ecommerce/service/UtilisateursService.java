package com.senservice.ecommerce.service;

import com.senservice.ecommerce.domain.Utilisateurs;
import com.senservice.ecommerce.enums.Role;
import com.senservice.ecommerce.model.UtilisateursDTO;
import com.senservice.ecommerce.repos.UtilisateursRepository;
import com.senservice.ecommerce.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UtilisateursService implements UserDetailsService {

    private final UtilisateursRepository utilisateursRepository;

    public UtilisateursService(final UtilisateursRepository utilisateursRepository) {
        this.utilisateursRepository = utilisateursRepository;
    }

    public List<UtilisateursDTO> findAll() {
        final List<Utilisateurs> utilisateurss = utilisateursRepository.findAll(Sort.by("userId"));
        return utilisateurss.stream()
                .map(utilisateurs -> mapToDTO(utilisateurs, new UtilisateursDTO()))
                .toList();
    }

    public UtilisateursDTO get(final Long userId) {
        return utilisateursRepository.findById(userId)
                .map(utilisateurs -> mapToDTO(utilisateurs, new UtilisateursDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Utilisateurs create(final UtilisateursDTO utilisateursDTO) {
        final Utilisateurs utilisateurs = new Utilisateurs();
        utilisateurs.setRole(Role.USER);
        mapToEntity(utilisateursDTO, utilisateurs);
        return utilisateursRepository.save(utilisateurs);
    }

    public void update(final Long userId, final UtilisateursDTO utilisateursDTO) {
        final Utilisateurs utilisateurs = utilisateursRepository.findById(userId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(utilisateursDTO, utilisateurs);
        utilisateursRepository.save(utilisateurs);
    }

    public void delete(final Long userId) {
        utilisateursRepository.deleteById(userId);
    }

    private UtilisateursDTO mapToDTO(final Utilisateurs utilisateurs,
            final UtilisateursDTO utilisateursDTO) {
        utilisateursDTO.setNom(utilisateurs.getNom());
        utilisateursDTO.setPrenom(utilisateurs.getPrenom());
        utilisateursDTO.setEmail(utilisateurs.getEmail());
        utilisateursDTO.setPassword(utilisateurs.getPassword());
        utilisateursDTO.setAdresse(utilisateurs.getAdresse());
        return utilisateursDTO;
    }

    private Utilisateurs mapToEntity(final UtilisateursDTO utilisateursDTO,
            final Utilisateurs utilisateurs) {
        utilisateurs.setNom(utilisateursDTO.getNom());
        utilisateurs.setPrenom(utilisateursDTO.getPrenom());
        utilisateurs.setEmail(utilisateursDTO.getEmail());
        utilisateurs.setPassword(utilisateursDTO.getPassword());
        utilisateurs.setAdresse(utilisateursDTO.getAdresse());
        return utilisateurs;
    }

    public boolean emailExists(final String email) {
        return utilisateursRepository.existsByEmailIgnoreCase(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        boolean emailVerified = emailExists(email);
        if (emailVerified == false){
            throw new UsernameNotFoundException("user not found");
        }
        Utilisateurs user = utilisateursRepository.findByEmail(email);
        return user ;
    }
}
