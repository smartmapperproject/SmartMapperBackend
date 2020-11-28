package com.smartmapper.core.domain.builder;

import java.util.Set;

import com.smartmapper.core.domain.model.Adresse;
import com.smartmapper.core.domain.model.Categorie;
import com.smartmapper.core.domain.model.Itineraire;
import com.smartmapper.core.domain.model.PointInteret;

public interface PointInteretBuilder {
    PointInteretBuilder withItineraire(Itineraire itineraire);
    PointInteretBuilder withAdresse(Adresse adresse);
    PointInteretBuilder withCategorie(Set<Categorie> categories);
    PointInteret build();
}
