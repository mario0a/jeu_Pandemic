package modele;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@NoArgsConstructor
@Data
public class TourJoueur {
    private LinkedList <String> tourDe = new LinkedList<>();

    public TourJoueur(List<Partie1Joueur> lesJoueurs){
        List<String> lesNomsJoueurs = new ArrayList<>(lesJoueurs.stream().map(Partie1Joueur::getNom).toList());
        Collections.shuffle(lesNomsJoueurs);
        tourDe.addAll(lesNomsJoueurs);
    }

    public String aQuiLeTour(){
        return tourDe.peek();
    }

    public void suivant(){
        tourDe.offer(tourDe.poll());
    }
}
