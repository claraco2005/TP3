/* Clara Kabran */
package h25.msd.poo2.etu.algo;

import h25.msd.poo2.echange.AlgorithmeAvecParametreI;
import h25.msd.poo2.echange.ApplicationUI;
import h25.msd.poo2.etu.classeDelegue.CompteurUsage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe représentant l'algorithme MixeurCle.
 * Cet algorithme mélange les caractères du texte en fonction d'une clé composée de chiffres de 1 à 4.
 * Chaque chiffre indique de combien de positions intervertir les lettres.
 */
public class MixeurCle implements AlgorithmeAvecParametreI {

    private CompteurUsage compteurUsage;
    private String cle = "";
    private ApplicationUI applicationUI;


    public MixeurCle() {
        this.compteurUsage = new CompteurUsage();
    }

    /**
     * Chiffre un texte en déplaçant ses lettres selon la clé définie.
     *
     * @param texteLisible le texte original à chiffrer
     * @return le texte mélangé
     */
    @Override
    public String encrypte(String texteLisible) {
        double momentDebut = System.nanoTime();
        assert texteLisible != null : "Le texte lisible ne doit pas être null";
        String chaineCryptee = "";

        if (cle.isEmpty()) return texteLisible;

        StringBuilder texte = new StringBuilder(texteLisible);
        int indexCle = 0;

        for (int i = 0; i < texte.length() - 4; i++) {
            int decalage = cle.charAt(indexCle % cle.length()) - '0';
            int j = i + decalage;

            if (j < texte.length()) {
                char temp = texte.charAt(i);
                texte.setCharAt(i, texte.charAt(j));
                texte.setCharAt(j, temp);
            }

            indexCle++;
        }
        chaineCryptee = texte.toString();

        Resultat resultat = new Resultat(this, LocalDateTime.now(), momentDebut, texteLisible, chaineCryptee);
        applicationUI.ajouteResultat(resultat);
        compteurUsage.increaseCompteur();
        return chaineCryptee;
    }

    /**
     * Déchiffre un texte mélangé en inversant les déplacements appliqués.
     *
     * @param texteCrypte le texte mélangé à déchiffrer
     * @return le texte original
     */
    @Override
    public String decrypte(String texteCrypte) {
        double momentDebut = System.nanoTime();
        assert texteCrypte != null : "Le texte crypté ne doit pas être null";
        String chaineDeCryptee = "";

        if (cle.isEmpty()) return texteCrypte;

        StringBuilder texte = new StringBuilder(texteCrypte);
        List<Integer> positions = new ArrayList<>();
        int indexCle = 0;

        for (int i = 0; i < texte.length() - 4; i++) {
            int decalage = cle.charAt(indexCle % cle.length()) - '0';
            if (i + decalage < texte.length()) {
                positions.add(i);
            }
            indexCle++;
        }

        for (int k = positions.size() - 1; k >= 0; k--) {
            int i = positions.get(k);
            int decalage = cle.charAt(k % cle.length()) - '0';
            int j = i + decalage;

            char temp = texte.charAt(i);
            texte.setCharAt(i, texte.charAt(j));
            texte.setCharAt(j, temp);
        }
        chaineDeCryptee = texte.toString();

        Resultat resultat = new Resultat(this, LocalDateTime.now(), momentDebut, texteCrypte, chaineDeCryptee);
        applicationUI.ajouteResultat(resultat);
        compteurUsage.increaseCompteur();
        return chaineDeCryptee;
    }

    /**
     * Définit la clé utilisée pour mélanger ou démêler les caractères.
     *
     * @param nom    le nom du paramètre (attendu "cle")
     * @param valeur la valeur de la clé
     */
    @Override
    public void setParametre(String nom, String valeur) {
        cle = valeur;
        getParametres().replace(nom, valeur);
    }

    /**
     * Retourne la valeur d'un paramètre donné.
     *
     * @param nom le nom du paramètre
     * @return la valeur associée
     */
    @Override
    public String getParametre(String nom) {
        return getParametres().get(nom);
    }

    /**
     * Valide que la clé ne contient que des chiffres de 1 à 4.
     *
     * @param nom    le nom du paramètre
     * @param valeur la valeur saisie
     * @return un message d'erreur ou une chaîne vide si valide
     */
    @Override
    public String valideParametre(String nom, String valeur) {
        String message = "";
        if (!valeur.matches("[1-4]+"))
            message = "La clé doit contenir seulement des chiffres de 1 à 4";
        return message;
    }

    /**
     * Retourne l'ensemble des paramètres de l'algorithme.
     *
     * @return une map contenant la clé
     */
    @Override
    public Map<String, String> getParametres() {
        Map<String, String> parametres = new HashMap<>(Map.of("cle pour Mixage ", cle));
        return parametres;
    }

    /**
     * Retourne le nom de l'algorithme.
     *
     * @return "Mixeur Clé"
     */
    @Override
    public String getNom() {
        return "Mixeur Clé";
    }

    /**
     * Retourne le nombre total d'utilisations de l'algorithme.
     *
     * @return compteur d'utilisation
     */
    @Override
    public int getNombreUsages() {
        return compteurUsage.getCompteur();
    }

    /**
     * Associe l'application UI pour d'éventuelles interactions futures.
     *
     * @param tp2Controller le contrôleur de l'interface utilisateur
     */
    @Override
    public void setApplicationUI(ApplicationUI tp2Controller) {
        applicationUI = tp2Controller;
    }

    @Override
    public String toString() {
        return " MixeurCle ";
    }
}