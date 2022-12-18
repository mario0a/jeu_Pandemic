import modele.CouleursMaladie;
import modele.Joueur;
import modele.Plateau;
import modele.Ville;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestVille {

    Ville paris = new Ville("paris","france",2, CouleursMaladie.BLEU,2000000,105,false);
    Ville alger = new Ville("alger","algerie",4, CouleursMaladie.ROUGE,7000000,363,false);
    Ville bagdad = new Ville("bagdad","Irak",1, CouleursMaladie.JAUNE,7650000,204,false);

    //création d'un plateau avec 3 joueurs

    Plateau plateau1 = new Plateau();
    Joueur amine=new Joueur("amine","amine123");
    Joueur lauriche=new Joueur("lauriche","lauriche123");
    Joueur pierre=new Joueur("pierre","pierre123");

    @Test
    public void testSetResearchLab() {
        paris.setaUnCentreDeRecherche(false);
        assertEquals(false,paris.getaUnCentreDeRecherche());
    }

    @Test
    public void testGetName() {
        assertEquals(paris.getNomVille(), "paris");
    }

    @Test
    public void testHasResearchLab() {
        assertEquals(false,paris.getaUnCentreDeRecherche());
    }

    @Test
    public void testAddCubesMaladies() {
        //ajout de 1 cube noir et 1 cube rouge sur paris
        paris.ajouterCube(CouleursMaladie.ROUGE);
        paris.ajouterCube(CouleursMaladie.NOIR);

        assertEquals(1,paris.getNombreDeCube());
        assertEquals(1,paris.getNombreDeCube());


    }

    @Test
    public void testAddVoisinLiee() {
        paris.ajouterVillesLiees(bagdad);
        paris.ajouterVillesLiees(alger);
        Set<Ville> expected = new HashSet<Ville>();
        expected.add(bagdad);
        expected.add(alger);
        assertEquals(expected ,paris.getVillesLiees());
    }









}
