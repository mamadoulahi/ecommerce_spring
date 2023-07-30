package com.senservice.ecommerce;

import com.senservice.ecommerce.config.RsaKeysConfig;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Ecommerce Backend",
                description = "Ce backend de l'application ECommerce dans Flutter gère toutes les opérations côté serveur pour la gestion des utilisateurs, des produits, des catégories et des commandes. Il fournit des API pour l'inscription et la connexion des utilisateurs, la gestion des produits et des catégories, ainsi que le traitement des commandes. Il est responsable de l'interaction avec la base de données pour stocker et récupérer les données nécessaires. Il utilise également le service de notification push pour envoyer des notifications aux utilisateurs après avoir effectué une commande. L'API du backend est documentée à l'aide de Swagger pour faciliter l'intégration et l'utilisation.",
                version = "1.0.0",
                contact = @Contact(
                        name = "mamadou keita",
                        email = "keitamamadoulahi@gmail.com"
                )
        )
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
@EnableConfigurationProperties(RsaKeysConfig.class)
public class EcommerceApplication {

    public static void main(final String[] args) {
        SpringApplication.run(EcommerceApplication.class, args);
    }

}
