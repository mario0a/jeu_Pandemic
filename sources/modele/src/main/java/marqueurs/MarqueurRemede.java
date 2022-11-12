package marqueurs;

import objets.Couleurs;

public class MarqueurRemede {
    private String recto;
    private String verso;
    private Couleurs couleurMarqueur;

    public MarqueurRemede(String recto, String verso, Couleurs couleurMarqueur) {
        this.recto = recto;
        this.verso = verso;
        this.couleurMarqueur = couleurMarqueur;
    }

    public String getRecto() {
        return recto;
    }

    public void setRecto(String recto) {
        this.recto = recto;
    }

    public String getVerso() {
        return verso;
    }

    public void setVerso(String verso) {
        this.verso = verso;
    }

    public Couleurs getCouleurMarqueur() {
        return couleurMarqueur;
    }

    public void setCouleurMarqueur(Couleurs couleurMarqueur) {
        this.couleurMarqueur = couleurMarqueur;
    }
}
