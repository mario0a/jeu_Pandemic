package objets;

import cartes.Carte;

//contient les différentes actions qu'on peut effectuer sur le plateau
public interface Actions {
   //permet au joueur de piocher des cartes dans les différentes piles de cartes
    public Carte piocherCarte();
    public void seDeplacer();
    public StationDeRecherche contruireUneStationDeRcherche();
    //permet de traiter une maladie dans la ville où on se trouve
    public void traiterUneMaladie();
    //permet de partager des connaissances avec un joueur en lui donnant ou en prenant la carte ville dans laquelle on se trouve
    public void partagerDesConnaissances();
    public void trouverRemede();


}
