import static org.junit.Assert.assertEquals;

import modele.CouleursMaladie;
import modele.Ville;
import org.junit.Test;
public class TestCarte {


        @Test
        public void testNomVille() {
            Ville ville = new Ville("paris");
            assertEquals("paris", ville.getNomVille());
        }

        @Test
        public void testCouleurMaladie() {
            String cm = String.valueOf(CouleursMaladie.BLEU);
            assertEquals(cm, "BLEU");
        }
    
}
