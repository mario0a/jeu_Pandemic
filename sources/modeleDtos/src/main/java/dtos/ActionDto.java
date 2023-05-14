package dtos;

public class ActionDto {
    private String idPartie;
    private String nomJoueur;
    private String element;
    public String getIdPartie() {
        return idPartie;
    }

    public ActionDto setIdPartie(String idPartie) {
        this.idPartie = idPartie;
        return this;
    }

    public String getNomJoueur() {
        return nomJoueur;
    }

    public ActionDto setNomJoueur(String nomJoueur) {
        this.nomJoueur = nomJoueur;
        return this;
    }

    public String getElement() {
        return element;
    }

    public ActionDto setElement(String element) {
        this.element = element;
        return this;
    }
}
