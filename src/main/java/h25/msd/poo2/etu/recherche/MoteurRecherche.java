
/* Danielle Mafouo Fouodji*/

package h25.msd.poo2.etu.recherche;

import h25.msd.poo2.echange.CritereTri;
import h25.msd.poo2.echange.MoteurRechercheI;
import h25.msd.poo2.echange.ResultatI;

import java.util.*;

public class MoteurRecherche implements MoteurRechercheI {

    List<ResultatI> listeResultat = new ArrayList<>();

    /**
     * Ajoute un résultat dans le moteur de recherche.
     *
     * @param resultat le résultat à ajouter
     */
    @Override
    public void ajouteResultat(ResultatI resultat) {
        listeResultat.add(resultat);
    }

    /**
     * Retourner tous les résultats dont le texte lisible contient le motRecherche
     *
     * @param motRecherche le mot à recherché (non null)
     * @return un Set de resultats
     */
    @Override
    public Set<ResultatI> recherche(String motRecherche) {
        Set<ResultatI> resultatRecherche = new HashSet<>();
        for (ResultatI resultatI : listeResultat) {
            if (resultatI.getTexteOriginal().contains(motRecherche)) {
                resultatRecherche.add(resultatI);
            }
        }
        return resultatRecherche;
    }

    /**
     * Retourne tous les résultats de recherche
     *
     * @return les résultats de recherche
     */

    @Override
    public Collection<ResultatI> getResultats() {

        return new ArrayList<>(listeResultat);
    }

    /**
     * reçoit une liste de résultat qu’elle doit trier selon le critère de tri reçu en paramètre.
     *
     * @param critereTri le critere de tri a prendre en consideration
     * @param resultats  la liste de resultat a trier
     */
    @Override
    public void triResultat(CritereTri critereTri, List<ResultatI> resultats) {

        if (critereTri == CritereTri.ALGO_PUIS_TAILLE_TEXTE_ORIGINAL) {
            resultats.sort(new CompareTypeTaille());

        } else if (critereTri == critereTri.ALGO_PUIS_DATE) {
            resultats.sort(new CompareTypeDate());
        } else if (critereTri == critereTri.NOMBRE_UTILISATION_PUIS_DATE) {
            resultats.sort(new CompareNbreUtilisationDate());
        }

    }
}
