package com.senservice.ecommerce.rest;

import com.senservice.ecommerce.domain.Utilisateurs;
import com.senservice.ecommerce.model.LoginDto;
import com.senservice.ecommerce.model.UtilisateursDTO;
import com.senservice.ecommerce.service.UtilisateursService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class UtilisateursResource {

    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private JwtEncoder jwtEncoder;

    private final UtilisateursService utilisateursService;

    public UtilisateursResource(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtEncoder jwtEncoder, final UtilisateursService utilisateursService) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
        this.utilisateursService = utilisateursService;
    }

    @GetMapping
    public ResponseEntity<List<UtilisateursDTO>> getAllUtilisateurss() {
        return ResponseEntity.ok(utilisateursService.findAll());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UtilisateursDTO> getUtilisateurs(
            @PathVariable(name = "userId") final Long userId) {
        return ResponseEntity.ok(utilisateursService.get(userId));
    }

    @PostMapping("/create")
    @ApiResponse(responseCode = "201")
    public Utilisateurs createUtilisateurs(
            @RequestBody @Valid final UtilisateursDTO utilisateursDTO) {
        utilisateursDTO.setPassword(passwordEncoder.encode(utilisateursDTO.getPassword()));
        return utilisateursService.create(utilisateursDTO);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Long> updateUtilisateurs(@PathVariable(name = "userId") final Long userId,
            @RequestBody @Valid final UtilisateursDTO utilisateursDTO) {
        utilisateursService.update(userId, utilisateursDTO);
        return ResponseEntity.ok(userId);
    }

    @DeleteMapping("/{userId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteUtilisateurs(
            @PathVariable(name = "userId") final Long userId) {
        utilisateursService.delete(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public Map<String,String> login(@RequestBody @Valid LoginDto loginDto){

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(),
                loginDto.getPassword()
        );
        authentication = authenticationManager.authenticate(authentication);
        Map<String,String> idToken = new HashMap<>();
        idToken.put("accessToken",jwtEncoder.encode(JwtEncoderParameters.from(createJwtClaimsSet(authentication))).getTokenValue());
        return idToken;

    }

    public JwtClaimsSet createJwtClaimsSet(Authentication authentication){
        Object principal = authentication.getPrincipal();
        Utilisateurs utilisateurs = (Utilisateurs) principal;
        Long userId = utilisateurs.getUserId();
        String email = utilisateurs.getEmail();
        String nom = utilisateurs.getNom();
        String prenom = utilisateurs.getPrenom();
        String scope = authentication.getAuthorities().stream().map(auth -> auth.getAuthority()).collect(Collectors.joining(" "));
        return  JwtClaimsSet.builder().subject(prenom)
                .claim("scope",scope)
                .claim("userId",userId)
                .claim("email",email)
                .claim("nom",nom)
                .build();
    }

}
