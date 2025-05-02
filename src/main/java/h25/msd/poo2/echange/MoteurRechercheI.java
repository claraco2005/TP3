package h25.msd.poo2.echange;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Interface pour un moteur de recherche qui peut retrouver tous les résultats qui contienne un mot donné.
 */
public interface MoteurRechercheI {

    /**
     * Ajoute le resultat dans l'historique. La méthode prépare également l'historique pour être capable
     * de fournir rapidement tous les résultats qui contiennent un mot donné.
     *
     * @param resultat  le résultat à ajouter
     */
    void ajouteResultat( ResultatI resultat);


    /**
     * Recherche tous les résultats qui contiennent le mot passé en paramètre. Si le paramètre est vide,
     * la méthode doit retourner tous les résultats emmagasinés dans le moteur.
     *
     * @param motRecherche le mot à recherché (non null)
     * @return tous les résultats contenant le mot demandé en paramètre ou tous les résultats si le paramètre eset vide.
     */
    Set<ResultatI> recherche(String motRecherche);

    /**
     * Retourne tous les résultats contenus dans le moteur.
     * @return tous les résultats contenus dans le moteur.
     */
    Collection<ResultatI> getResultats();

    /**
     *
     * @param critereTri
     * @param resultats
     */
    public void triResultat(CritereTri critereTri, List<ResultatI> resultats) ;



}
