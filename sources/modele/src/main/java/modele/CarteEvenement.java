package modele;

import modele.interfaces.ICartes;

public class CarteEvenement implements ICartes {
    private String nom;
    private TypeCarte typeCarte;

    public CarteEvenement(String nom, TypeCarte typeCarte) {
        this.nom = nom;
        this.typeCarte = typeCarte;
    }

    @Override
    public TypeCarte typeCarte() {
        return this.typeCarte;
    }

    @Override
    public String informations() {
        return this.nom;
    }
}
