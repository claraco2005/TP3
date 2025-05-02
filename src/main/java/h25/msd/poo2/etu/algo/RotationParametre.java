/*Mame Diarra Bousso Ndiaye*/
package h25.msd.poo2.etu.algo;

import h25.msd.poo2.echange.AlgorithmeAvecParametreI;
import h25.msd.poo2.echange.AlgorithmeI;
import h25.msd.poo2.echange.ApplicationUI;
import h25.msd.poo2.etu.classeDelegue.CompteurUsage;
import h25.msd.poo2.etu.classeDelegue.RemplisseurMatrice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class RotationParametre implements AlgorithmeAvecParametreI, AlgorithmeI {
    private String nomRangee;
    private String nomColonne;
    private String valeurRangee;
    private String valeurColonne;
    private Map<String, String> parametres = new HashMap<>();
    private ApplicationUI applicationUI;
    private RemplisseurMatrice remplisseurMatrice;
    private CompteurUsage compteurUsage;

    public RotationParametre() {
        this.nomRangee = "rangee";
        this.nomColonne = "colonne";
        this.valeurRangee = "2";
        this.valeurColonne = "4";
        this.parametres.put(this.nomRangee, this.valeurRangee);
        this.parametres.put(this.nomColonne, this.valeurColonne);
        remplisseurMatrice = new RemplisseurMatrice();
        this.compteurUsage = new CompteurUsage();

    }


    /**
     * {@inheritDoc}
     *
     * @param nom    le nom du paramètre (non null et non vide)
     * @param valeur la valeur du parametre (non null)
     */
    @Override
    public void setParametre(String nom, String valeur) {
        assert nom != null && !nom.isEmpty() : "nom ne doitpas etre vide";
        assert valeur != null : "Valeur ne doit pas etre null";

        parametres.put(nom, valeur);

    }

    /**
     * {@inheritDoc}
     *
     * @param nom le nom du paramètre à récupérer (non null et non vide)
     * @return
     */
    @Override
    public String getParametre(String nom) {
        String valeur;
        valeur = getParametres().get(nom);
        return valeur;
    }

    /**
     * {@inheritDoc}
     * validations: les caractèdres de la clé doivent être dans les caractères permis
     *
     * @param valeur {@inheritDoc}(non null, non vide et seulement les cacractères permis)
     */
    @Override
    public String valideParametre(String nom, String valeur) {
        assert valeur != null && !valeur.isEmpty() : "Valeur ne doit pas etre null";

        String message = "";

        int valeurNumerique = Integer.parseInt(valeur);
        Map<String, String> parametres = getParametres();
        if (parametres.containsKey(nom)) {
            if (valeurNumerique <= 0) {
                message = "La valeur du paramètre ne peut pas être inferieur à 0.";
            } else {
                message = "";
            }

        }

        return message;
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public Map<String, String> getParametres() {
        return new HashMap<>(parametres);
    }


    /**
     * Crypte le texte en utilisant une matrice de rotation
     *
     * @param texteLisible
     * @return le texte crypte
     */
    @Override
    public String encrypte(String texteLisible) {
        double momentDebut = System.nanoTime();

        int rangee = Integer.parseInt(valeurRangee);
        int colonne = Integer.parseInt(valeurColonne);

        assert rangee > 0 && colonne > 0;

        String texteCrypte = "";
        char[] caractereTexte = texteLisible.toCharArray();
        int index = 0;
        int tailleMatrice = rangee * colonne;

        while (index < caractereTexte.length) {  // A chaque fois qu'il reste des lettres on recommence en creant une matrice
            char[][] matrice = remplisseurMatrice.remplirMatrice(texteLisible, index, rangee, colonne);

            char[][] matrice2 = transposerMatrice(matrice, rangee, colonne); //creation d'une matrice pour la transposition

            texteCrypte += extraireTexteDeMatrice(matrice2);// on parcourt la matrice transposee pour ajouterchac caractere a la chaine de texte


            index = calculerLeNouveauIndex(index, tailleMatrice);

        }
        Resultat resultat = new Resultat(this, LocalDateTime.now(), momentDebut, texteLisible, texteCrypte);
        applicationUI.ajouteResultat(resultat);
        compteurUsage.increaseCompteur();
        return texteCrypte;
    }


    /**
     * decrypte en utilisant une matrice de rotation
     *
     * @param texteEncrypte
     * @return le texte decrypte
     */
    @Override
    public String decrypte(String texteEncrypte) {
        double momentDebut = System.nanoTime();

        int rangee = Integer.parseInt(valeurRangee);
        int colonne = Integer.parseInt(valeurColonne);

        assert texteEncrypte != null && !texteEncrypte.isEmpty();
        assert colonne > 0 && rangee > 0;

        String texteDecrypte = "";
        char[] caractereTexte = texteEncrypte.toCharArray();
        int index = 0;
        int tailleMatrice = rangee * colonne;
        while (index < caractereTexte.length) {
            char[][] matrice = remplisseurMatrice.remplirMatrice(texteEncrypte, index, colonne, rangee);

            char[][] matriceTransposee = transposerMatrice(matrice, colonne, rangee);

            texteDecrypte += extraireTexteDeMatrice(matriceTransposee);

            index = calculerLeNouveauIndex(index, tailleMatrice);
        }
        texteDecrypte = texteDecrypte.replaceAll("#", "");

        Resultat resultat = new Resultat(this, LocalDateTime.now(), momentDebut, texteEncrypte, texteDecrypte);
        applicationUI.ajouteResultat(resultat);
        compteurUsage.increaseCompteur();
        return texteDecrypte;
    }

    /**
     * Calcule le nouveau index en fonction de la taille de la matrice
     *
     * @param index
     * @param TailleMatrice
     * @return le nouveau index
     */
    private int calculerLeNouveauIndex(int index, int TailleMatrice) {
        return index + TailleMatrice;
    }


    /**
     * transpose une matrice en inversant ses lignes et ses colonnes
     *
     * @param matrice
     * @param rangee
     * @param colonne
     * @return la matrcie transposee
     */
    private char[][] transposerMatrice(char[][] matrice, int rangee, int colonne) {
        char[][] transpose = new char[colonne][rangee];
        for (int i = 0; i < transpose.length; i++) {
            for (int j = 0; j < transpose[i].length; j++) {
                transpose[i][j] = matrice[j][i];
            }
        }
        return transpose;
    }

    /**
     * Extrait le texte d'une matrice en parcourant ses caractères
     *
     * @param matrice
     * @return le texte final
     */
    private String extraireTexteDeMatrice(char[][] matrice) {
        StringBuilder texte = new StringBuilder();
        for (char[] chars : matrice) {
            for (char aChar : chars) {
                texte.append(aChar);
            }
        }
        return texte.toString();
    }


    @Override
    public String getNom() {
        return "RotationParamètre";
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
        return " RotationParamètre ";
    }
}
