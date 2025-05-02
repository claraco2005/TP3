/* Clara Kabran */
package h25.msd.poo2.etu.algo;

import h25.msd.poo2.echange.AlgorithmeI;
import h25.msd.poo2.echange.ApplicationUI;
import h25.msd.poo2.etu.classeDelegue.CompteurUsage;

import java.time.LocalDateTime;

/**
 * Algorithme DécalageParMot.
 * Cet algorithme chiffre et déchiffre un texte en décalant chaque lettre
 * selon la longueur du mot auquel elle appartient.
 * Exemple : un mot de 5 lettres fait avancer chaque lettre de +5 positions.
 */
public class DecalageParMot implements AlgorithmeI {

    private ApplicationUI applicationUI;
    private CompteurUsage compteurUsage;

    /**
     * Constructeur : instancie l'algorithme.
     */
    public DecalageParMot() {
        this.compteurUsage = new CompteurUsage();
    }

    /**
     * Chiffre un texte lisible.
     * Chaque mot est traité séparément : chaque lettre est décalée
     * d'autant de positions que la longueur du mot.
     *
     * @param texteLisible Le texte original à chiffrer.
     * @return Le texte après chiffrement.
     */
    @Override
    public String encrypte(String texteLisible) {
        double momentDebut = System.nanoTime();
        assert texteLisible != null : "Le texte lisible ne doit pas être null";

        String[] mots = texteLisible.split(" ");
        StringBuilder resultats = new StringBuilder();

        for (int i = 0; i < mots.length; i++) {
            resultats.append(decalerMot(mots[i], mots[i].length()));
            if (i != mots.length - 1) {
                resultats.append(" ");
            }
        }
        Resultat resultat = new Resultat(this, LocalDateTime.now(), momentDebut, texteLisible, resultats.toString());
        applicationUI.ajouteResultat(resultat);
        compteurUsage.increaseCompteur();
        return resultats.toString();
    }

    /**
     * Déchiffre un texte crypté.
     * Applique l'inverse du chiffrement en reculant les lettres
     * selon la longueur du mot.
     *
     * @param texteCrypte Le texte crypté à déchiffrer.
     * @return Le texte original après décryptage.
     */
    @Override
    public String decrypte(String texteCrypte) {
        double momentDebut = System.nanoTime();
        assert texteCrypte != null : "Le texte crypté ne doit pas être null";

        String[] mots = texteCrypte.split(" ");
        StringBuilder resultats = new StringBuilder();

        for (int i = 0; i < mots.length; i++) {
            resultats.append(decalerMot(mots[i], -mots[i].length()));
            if (i != mots.length - 1) {
                resultats.append(" ");
            }
        }
        Resultat resultat = new Resultat(this, LocalDateTime.now(), momentDebut, texteCrypte, resultats.toString());
        applicationUI.ajouteResultat(resultat);
        compteurUsage.increaseCompteur();
        return resultats.toString();
    }

    /**
     * Applique un décalage sur chaque lettre d'un mot.
     * Les caractères qui ne sont pas des lettres ou des chiffres
     * sont laissés inchangés.
     *
     * @param mot      Le mot à traiter.
     * @param decalage La valeur du décalage (positive pour chiffrement, négative pour déchiffrement).
     * @return Le mot transformé après décalage.
     */
    private String decalerMot(String mot, int decalage) {
        StringBuilder nouveauMot = new StringBuilder();

        for (int i = 0; i < mot.length(); i++) {
            char c = mot.charAt(i);

            if (Character.isLetterOrDigit(c)) {
                int nouveauCode = (c + decalage);
                nouveauMot.append((char) nouveauCode);
            } else {
                nouveauMot.append(c);
            }
        }

        return nouveauMot.toString();
    }


    @Override
    public String getNom() {
        return "Décalage Par Mot";
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
        return "Décalage Par Mot";
    }


}