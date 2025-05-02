
/* Danielle Mafouo Fouodji*/

package h25.msd.poo2.etu.recherche;

import h25.msd.poo2.echange.ResultatI;

import java.util.Comparator;

public class CompareTypeDate implements Comparator<ResultatI> {

    /**
     * Compare la liste de resultats en fonction du type d'algo puis de la date
     *
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     * @return le resultat
     */
    @Override
    public int compare(ResultatI o1, ResultatI o2) {
        int resultat = o1.getAlgoUtilise().getNom().compareTo(o2.getAlgoUtilise().getNom());
        if (resultat == 0) {
            resultat = o2.getQuand().compareTo(o1.getQuand());

        }
        return resultat;
    }
}
