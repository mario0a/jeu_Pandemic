package facade;

public class CarteEpidemie implements ICartes{
    private String nom;
    private TypeCarte typeCarte;

    public CarteEpidemie(String nom, TypeCarte typeCarte) {
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
