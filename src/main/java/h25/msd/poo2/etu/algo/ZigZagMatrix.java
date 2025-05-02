/*Mame Diarra Bousso Ndiaye*/
/*Algorythme d'encryption/d'décryption utilisant un parcours en zigzag sur une matrice*/
package h25.msd.poo2.etu.algo;

import h25.msd.poo2.echange.AlgorithmeI;
import h25.msd.poo2.echange.ApplicationUI;
import h25.msd.poo2.etu.classeDelegue.CompteurUsage;
import h25.msd.poo2.etu.classeDelegue.RemplisseurMatrice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ZigZagMatrix implements AlgorithmeI {


    private CompteurUsage compteurUsages;
    private ApplicationUI applicationUI;
    private RemplisseurMatrice remplisseurMatrice;

    public ZigZagMatrix() {
        this.remplisseurMatrice = new RemplisseurMatrice();
        this.compteurUsages = new CompteurUsage();
    }

    /**
     * Crypte un texte lisible en le placant dans une matrice 2D,
     * puis en lisant les caractères en zigzag:
     * -colonne paire: du haut vers le bas,
     * -colonne impaire: du bas vers le haut.
     *
     * @param texteLisible texte à crypter
     * @return texteCrypte
     */
    @Override
    public String encrypte(String texteLisible) {
        assert texteLisible != null : "Le texte à encrypter ne doit pas etre null";

        long momentDebut = System.nanoTime();
        String texteCryptee = "";

        List<Integer> dimensions = calculerDimensionsMatrice(texteLisible);
        int taille1 = dimensions.get(0);
        int taille2 = dimensions.get(1);

        int index = 0;
        char[][] matrice = remplisseurMatrice.remplirMatrice(texteLisible, index, taille1, taille2);

        texteCryptee = lireMatriceEnZigZag(matrice);

        Resultat resultat = new Resultat(this, LocalDateTime.now(), momentDebut, texteLisible, texteCryptee);
        applicationUI.ajouteResultat(resultat);
        compteurUsages.increaseCompteur();

        return texteCryptee;
    }

    /**
     * Decrypte un texte en le replacant dans une matrice 2D en zigzag,
     * puis en lisant les caractère ligne par ligne
     *
     * @param texteEncrypte texte à crypter
     * @return texteDecrypte
     */
    @Override
    public String decrypte(String texteEncrypte) {
        assert texteEncrypte != null : "Le texte à décrypter ne doit pas etre null";

        long momentDebut = System.nanoTime();
        String texteDecrypte = "";

        List<Integer> dimensions = calculerDimensionsMatrice(texteEncrypte);
        int taille1 = dimensions.get(0);
        int taille2 = dimensions.get(1);
        char[][] matrice = remplirMatriceZigZag(texteEncrypte, taille1, taille2);


        for (char[] chars : matrice) {
            for (int j = 0; j < chars.length; j++) {
                texteDecrypte += chars[j];
            }

        }
        Resultat resultat = new Resultat(this, LocalDateTime.now(), momentDebut, texteEncrypte, texteDecrypte);
        applicationUI.ajouteResultat(resultat);
        compteurUsages.increaseCompteur();
        return texteDecrypte.replace('#', ' ');
    }

    /**
     * Calcule les dimensions (rangées et colonnes) pour construire
     * une matrice auss proche d'un carré que possible.
     *
     * @param texteLisible texte à mettre dans la matrcie
     * @return une liste triée contenant les dimensions ( rangées, colonnes)
     */
    private List<Integer> calculerDimensionsMatrice(String texteLisible) {
        assert texteLisible != null && !texteLisible.isEmpty() : "lE texte doit etre non vide pour calculer les dimensions";
        int produit = texteLisible.length();
        int racine = (int) Math.ceil(Math.sqrt(produit)); // Math.ceil pour arrondir au chiffre le plis petit entier>= au nombre et sqr pour la racine

        int rangee = racine;
        int colonnes = racine;

        //Si le carre est un peu trop grand par exemple produit = 10 et racine = 4
        if (rangee * (colonnes - 1) >= produit) {
            colonnes = colonnes - 1;
        }

        List<Integer> dimensions = new ArrayList<>();
        dimensions.add(rangee);
        dimensions.add(colonnes);
        Collections.sort(dimensions);  //la rangee sra la chiffre le plus petit
        return dimensions;
    }

    /**
     * Affiche la matrice donnée en parametre dans la console
     * (pour visualiser les matrice)
     *
     * @param matrice la matrice à afficher
     */
    private void afficherMatrice(char[][] matrice) {
        for (int i = 0; i < matrice.length; i++) {
            for (int j = 0; j < matrice[i].length; j++) {
                System.out.print(matrice[i][j] + " ");
            }
            System.out.println();

        }
    }

    /**
     * Lit une matrice en suivant un parcours en zigzag par colonnes
     *
     * @param matrice
     * @return
     */
    private String lireMatriceEnZigZag(char[][] matrice) {
        assert matrice != null && matrice.length > 0 && matrice[0].length > 0 : "LA matrice doit etre correctement remplie";

        StringBuilder texteCrypte = new StringBuilder();
        for (int colonne = 0; colonne < matrice[0].length; colonne++) {
            if (colonne % 2 == 0) {
                for (int rangee = 0; rangee < matrice.length; rangee++) {
                    texteCrypte.append(matrice[rangee][colonne]);
                }
            } else {
                for (int rangee = matrice.length - 1; rangee >= 0; rangee--) {
                    texteCrypte.append(matrice[rangee][colonne]);
                }
            }
        }
        return texteCrypte.toString();
    }

    /**
     * Remplit une matrice à partir d'un texte crypté en suivant
     * l'ordre zigzag par colonnes.
     *
     * @param texteCrypte texte à crypter
     * @param taille1     nombre de rangées
     * @param taille2     nombre de colonnes
     * @return matrcie remplie
     */
    private char[][] remplirMatriceZigZag(String texteCrypte, int taille1, int taille2) {
        assert texteCrypte != null : "Le txte ne doit pas etre null.";
        assert taille1 > 0 && taille2 > 0;
        assert taille1 * taille2 == texteCrypte.length() : "lA taille du texte doit correspondre aux dimensions de la matrice";

        int index = 0;
        char[] caractere = texteCrypte.toCharArray();
        char[][] matrice = new char[taille1][taille2];
        for (int colonne = 0; colonne < matrice[0].length; colonne++) {
            if (colonne % 2 == 0) {
                for (int rangee = 0; rangee < matrice.length; rangee++) {
                    matrice[rangee][colonne] = caractere[index];
                    index++;
                }

            } else {
                for (int rangee = matrice.length - 1; rangee >= 0; rangee--) {
                    matrice[rangee][colonne] = caractere[index];
                    index++;
                }

            }
        }
        //afficherMatrice(matrice); //(pour tester)
        return matrice;
    }


    @Override
    public String getNom() {
        return "ZigZagMatrix";
    }

    @Override
    public int getNombreUsages() {
        return compteurUsages.getCompteur();
    }

    @Override
    public void setApplicationUI(ApplicationUI tp2Controller) {
        applicationUI = tp2Controller;
    }

    @Override
    public String toString() {
        return " ZigZagMatrix ";
    }


}
