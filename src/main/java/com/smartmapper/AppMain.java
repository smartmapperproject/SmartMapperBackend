/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.smartmapper;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartmapper.core.domain.builder.AdresseBuilder;
import com.smartmapper.core.domain.model.Adresse;
import com.smartmapper.core.domain.model.AdresseBuilderImpl;
import com.smartmapper.core.domain.model.Categorie;
import com.smartmapper.core.domain.model.Coordonnees;
import com.smartmapper.core.domain.model.PointInteret;
import com.smartmapper.core.domain.model.PointInteretBuilderImpl;
import com.smartmapper.core.infra.repository.AdresseRepository;
import com.smartmapper.core.infra.repository.CategorieRepository;
import com.smartmapper.core.infra.repository.CoordonneesRepository;
import com.smartmapper.core.infra.service.serviceImpl.PointInteretService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import com.fasterxml.jackson.core.type.TypeReference;


@SpringBootApplication
@EnableJpaRepositories("com.smartmapper.core.infra.repository")
@EnableSwagger2
public class AppMain {
    public String getGreeting() {
        return "Hello dolphin.";
    }

    public static void main(String[] args) {
        SpringApplication.run(AppMain.class, args);
        System.out.println(new AppMain().getGreeting());
    }

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.smartmapper")).build();
    }

    /**
     * Permet de parser le fichier Json des poi et les sauvegarder dans la base de données
     * @param pInteretService
     * @param cRepository
     * @param aRepository
     * @param caRepository
     * @return
     */
    @Bean
    CommandLineRunner populateDatabase(PointInteretService pInteretService, CoordonneesRepository cRepository, AdresseRepository aRepository, CategorieRepository caRepository) {
        return args -> {
            final String userDirectory = System.getProperty("user.dir");
            final ObjectMapper objectMapper = new ObjectMapper();
            final List<JsonNode> pois = objectMapper.readValue(new File(userDirectory + "/src/main/resources/geojson-data.json"), new TypeReference<List<JsonNode>>(){});
            final Set<PointInteret> lesPoints= new HashSet<PointInteret>();
            pois.forEach(poi -> {

                final Categorie c1 = new Categorie(null, poi.get("fields").get("categorie1").asText());
                final Categorie c2 = new Categorie(null, poi.get("fields").get("categorie2").asText());
                final Categorie c3 = poi.get("fields").get("categorie3")!= null ? new Categorie(null, poi.get("fields").get("categorie3").asText()): null;

                final Set<Categorie> lCategories = new HashSet<>();
                lCategories.add(c1);
                lCategories.add(c2);
                lCategories.add(c3);

                //Creation de l'objet adresse du point d'interet
                final AdresseBuilder adresseBuilder = new AdresseBuilderImpl(poi.get("fields").get("titre").asText(),
                    poi.get("fields").get("adresse").asText())
                    .withVille(poi.get("fields").get("ville").asText());
                if(poi.get("fields").get("codepostal")!= null)
                    adresseBuilder.withCodePostal(poi.get("fields").get("codepostal").asText());
                if(poi.get("fields").get("telephone")!= null)
                    adresseBuilder.withTelephone(poi.get("fields").get("telephone").asText());
                Adresse adresse = adresseBuilder.build();

                // Creation de ses coordonnes et de sa description
                final String description;
                if(poi.get("fields").get("description")!= null)
                    description = poi.get("fields").get("description").asText();
                else
                    description = "";
                final Coordonnees coordonnees = new Coordonnees(poi.get("fields").get("longitude").doubleValue(),
                poi.get("fields").get("latitude").doubleValue(), null);
                
                //Creation de l'objet point d'interet
                final PointInteret unPoint = new PointInteretBuilderImpl(poi.get("fields").get("titre").asText(),
                    coordonnees, description)
                    .withAdresse(adresse)
                    .withCategorie(lCategories)
                    .build();
                
                lCategories.forEach(lca -> {
                    if(lca!=null)
                        unPoint.addCategorie(lca);
                });
                adresse.setPoint(unPoint);
                coordonnees.setPoint(unPoint);
                lesPoints.add(unPoint);

            });
            // Sauvegarde des nouvelles entités dans la base
            lesPoints.forEach(points -> pInteretService.save(points));
        };
    }
}
