package modele;

import modele.interfaces.ICartes;

public class CarteVille implements ICartes {
    private Ville ville;
    private TypeCarte typeCarte;

    public CarteVille(Ville ville, TypeCarte typeCarte) {
        this.ville = ville;
        this.typeCarte = typeCarte;
    }

    public Ville getVille() {
        return ville;
    }

    @Override
    public TypeCarte typeCarte() {
        return this.typeCarte;
    }

    @Override
    public String informations() {
        return this.ville.getNomVille();
    }
}
