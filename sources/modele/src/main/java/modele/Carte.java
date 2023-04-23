package modele;

public class Carte {
    private String information;
    private TypeCarte typeCarte;

    public Carte() {
    }

    public Carte(String information, TypeCarte typeCarte) {
        this.information = information;
        this.typeCarte = typeCarte;
    }

    public String getInformation() {
        return information;
    }

    public Carte setInformation(String information) {
        this.information = information;
        return this;
    }

    public TypeCarte getTypeCarte() {
        return typeCarte;
    }

    public Carte setTypeCarte(TypeCarte typeCarte) {
        this.typeCarte = typeCarte;
        return this;
    }

    @Override
    public String toString() {
        return "Carte{" +
                "information='" + information + '\'' +
                ", typeCarte=" + typeCarte +
                '}';
    }
}
