package h25.msd.poo2.echange;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Map;

public interface AlgorithmeAvecParametreI extends AlgorithmeI {

    /**
     * Ajoute ou modifie un paramètre. Peut être utilisé dans le constructeur pour
     * créer les paramètres nécessaires pour l'algorithme.
     *
     * @param nom    le nom du paramètre (non null et non vide)
     * @param valeur la valeur du parametre (non null)
     */
    void setParametre(String nom, String valeur);

    /**
     * Retourne la valeur d'un paramètre. Tous les paramètres sont emmagasinés sous forme
     * de chaine de caractères.
     *
     * @param nom le nom du paramètre à récupérer (non null et non vide)
     * @return la valeur du paramètre
     */
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
    String getParametre(String nom);

    /**
     * Valide le paramètre saisie dans le UI. Valide également que la clé demandée
     * existe bien pour cet algorithme.
     *
     * @param nom    le nom du paramètre à récupérer (non null et non vide)
     * @param valeur la valeur du paramètre à valider
     * @return un message d'erreur ou une chaine vide s'il n'y a pas d'erreur.
     */
    String valideParametre(String nom, String valeur);


    /**
     * retourne la map de tous les paramètres
     *
     * @return une map contenant tous les paramètres en clé et leur valeur en valeur
     */
    Map<String, String> getParametres();
}
