package com.smartmapper.core.domain.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

public class PointInteret {

    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
    private String name;
    private Coordonnees coordonnes;
    private String description;
    @ManyToOne
    private Itineraire itineraire;

    public PointInteret(String name, Coordonnees coordonnes, String description) {
        this.name = name;
        this.coordonnes = coordonnes;
        this.description = description;
    }
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordonnees getCoordonnes() {
        return coordonnes;
    }

    public void setCoordonnes(Coordonnees coordonnes) {
        this.coordonnes = coordonnes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("PointInteret [coordonnes=");
        builder.append(coordonnes);
        builder.append(", description=");
        builder.append(description);
        builder.append(", name=");
        builder.append(name);
        builder.append("]");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((coordonnes == null) ? 0 : coordonnes.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PointInteret other = (PointInteret) obj;
        if (coordonnes == null) {
            if (other.coordonnes != null)
                return false;
        } else if (!coordonnes.equals(other.coordonnes))
            return false;
        return true;
    }

    public PointInteret() {
    }
    
    
}
