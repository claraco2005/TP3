/* Danielle Mafouo Fouodji*/
package h25.msd.poo2.etu.algo;

import h25.msd.poo2.echange.AlgorithmeAvecParametreI;
import h25.msd.poo2.echange.ApplicationUI;
import h25.msd.poo2.etu.classeDelegue.CompteurUsage;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DecalageCle extends AbstractAlgoCryptographique implements AlgorithmeAvecParametreI {

    private CompteurUsage compteurUsage = new CompteurUsage();
    String decalage = "dany";
    private ApplicationUI applicationUI;


    /**
     * Encrypte la chaine de caractere recu en parametre en utilisant une cle qui est egalement une la chaine de caracteres s en utilisant chaque lettre de la clé pour effectuer les décalages successifs.
     *
     * @param texteLisible la chaine a encrypter
     * @return la chaine cryptee
     */
    @Override
    public String encrypte(String texteLisible) {
        assert texteLisible != null : "Le texte à encrypter est null";
        double momentDebut = System.nanoTime();
        String chaineCryptee = "";
        List<Integer> listeNombre = traduitTexteEnListe(texteLisible);
        List<Integer> listeValeurCle = traduitTexteEnListe(decalage);
        for (int i = 0; i < listeNombre.size(); i++) {
            int nombre = listeNombre.get(i);
            nombre = nombre + listeValeurCle.get(i % listeValeurCle.size());
            nombre = nombre % getCharMap().size();
            listeNombre.set(i, nombre);
        }
        chaineCryptee = traduitListeEnTexte(listeNombre);
        Resultat resultat = new Resultat(this, LocalDateTime.now(), momentDebut, texteLisible, chaineCryptee);
        applicationUI.ajouteResultat(resultat);
        compteurUsage.increaseCompteur();
        return chaineCryptee;
    }

    /**
     * decrypte  la chaine de caractere recue en parametre en faisant l'inverse de encrypte
     *
     * @param texteEncrypte le texte a decrypter
     * @return la chaine DeCryptee
     */
    @Override
    public String decrypte(String texteEncrypte) {
        assert texteEncrypte != null : "Le texte à décrypter est null";
        double momentDebut = System.nanoTime();
        String chaineDeCryptee = "";

        List<Integer> listeLettre = traduitTexteEnListe(texteEncrypte);
        List<Integer> listeValeurCle = traduitTexteEnListe(decalage);

        for (int i = 0; i < listeLettre.size(); i++) {
            int nombre = listeLettre.get(i);

            nombre = nombre - listeValeurCle.get(i % listeValeurCle.size());
            while (nombre < 0) {
                nombre = nombre + getCharMap().size();
            }
            listeLettre.set(i, nombre);
        }
        chaineDeCryptee = traduitListeEnTexte(listeLettre);
        Resultat resultat = new Resultat(this, LocalDateTime.now(), momentDebut, texteEncrypte, chaineDeCryptee);
        applicationUI.ajouteResultat(resultat);
        compteurUsage.increaseCompteur();
        return chaineDeCryptee;
    }

    /**
     * {inheritDoc}
     *
     * @param nom    le nom du paramètre (non null et non vide)
     * @param valeur la valeur du parametre (non null)
     */
    @Override
    public void setParametre(String nom, String valeur) {
        decalage = valeur;
        getParametres().replace(nom, valeur);
    }

    /**
     * {inheritDoc}
     *
     * @param nom le nom du paramètre à récupérer (non null et non vide)
     * @return getParametres().get(nom)
     */
    @Override
    public String getParametre(String nom) {
        return getParametres().get(nom);
    }

    /**
     * {inheritDoc}
     *
     * @param nom    le nom du paramètre à récupérer (non null et non vide)
     * @param valeur la valeur du paramètre à valider
     * @return message
     */
    @Override
    public String valideParametre(String nom, String valeur) {
        String message = "";
        if (!getParametres().containsKey(nom)) {
            message = "Cette clé n'existe pas dans la table";
        } else if (valeur.isEmpty()) {
            message = "Veuillez entrer une clé";
        }
        return message;
    }

    /**
     * {inherit}
     *
     * @return parametre
     */
    @Override
    public Map<String, String> getParametres() {
        Map<String, String> parametre = new HashMap<>(Map.of("cleParametre", decalage));
        return parametre;
    }


    @Override
    public String getNom() {
        return getClass().getSimpleName();
    }

    @Override
    public int getNombreUsages() {
        return compteurUsage.getCompteur();
    }

    @Override
    public void setApplicationUI(ApplicationUI tp2Controller) {
        applicationUI = tp2Controller;
    }

    @Override
    public String toString() {
        return " DecalageCle ";
    }


}
