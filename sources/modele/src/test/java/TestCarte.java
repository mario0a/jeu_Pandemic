import static org.junit.Assert.assertEquals;

import cartes.Carte;
import modele.CarteVille;
import modele.CouleursMaladie;
import modele.TypeCarte;
import modele.Ville;
import org.junit.Test;
public class TestCarte {


    @Test
    public void testNomVille() {
        Carte carte = new Carte("paris",TypeCarte.VILLE);
        assertEquals("paris", carte.getNomCarte());
    }

    @Test
    public void testPlusieursNomVille() {
        Carte atlanta = new Carte("atlanta",TypeCarte.VILLE);
        Carte chicago = new Carte("chicago",TypeCarte.VILLE);

        assertEquals("Atlanta", atlanta.getNomCarte());
        assertEquals("Chicago", chicago.getNomCarte());
    }

    @Test
    public void testCouleurMaladie() {
        Carte carte = new Carte("atlanta",TypeCarte.VILLE,CouleursMaladie.BLEU);
        assertEquals(CouleursMaladie.BLEU, carte.getCouleursMaladie());
    }

    @Test
    public void testCreerCartePropagation() {
        Carte propagation = new Carte("atlanta",TypeCarte.PROPAGATION,CouleursMaladie.BLEU);
        assertEquals(TypeCarte.PROPAGATION, propagation.getTypeCarte());
        assertEquals("atlanta", propagation.getNomCarte());
        assertEquals(CouleursMaladie.BLEU, propagation.getCouleursMaladie());
    }

    
}
