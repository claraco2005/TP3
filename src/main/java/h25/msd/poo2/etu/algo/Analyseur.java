/* Clara Kabran */

package h25.msd.poo2.etu.algo;

import h25.msd.poo2.echange.AlgorithmeI;
import h25.msd.poo2.echange.ApplicationUI;
import h25.msd.poo2.etu.classeDelegue.CompteurUsage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Analyseur implements AlgorithmeI {

    private ApplicationUI applicationUI;
    CompteurUsage compteurUsage;

    public Analyseur() {
        this.compteurUsage = new CompteurUsage();
    }

    /**
     * Chiffre un texte en associant chaque caractère à ses positions d'apparition dans le texte.
     *
     * @param texteLisible le texte original à chiffrer
     * @return une chaîne contenant les caractères suivis de leurs positions, séparés par des #
     */
    @Override
    public String encrypte(String texteLisible) {
        assert texteLisible != null : "Le texte lisible ne doit pas être null";
        double momentDebut = System.nanoTime();

        Map<Character, List<Integer>> mapPositions = new LinkedHashMap<>();

        for (int i = 0; i < texteLisible.length(); i++) {
            char c = texteLisible.charAt(i);
            mapPositions.putIfAbsent(c, new ArrayList<>());
            mapPositions.get(c).add(i);
        }

        StringBuilder crypte = new StringBuilder();
        for (Map.Entry<Character, List<Integer>> entry : mapPositions.entrySet()) {
            crypte.append(entry.getKey());
            for (int pos : entry.getValue()) {
                crypte.append("#").append(pos);
            }
            crypte.append("#");
        }
        Resultat resultat = new Resultat(this, LocalDateTime.now(), momentDebut, texteLisible, crypte.toString());
        applicationUI.ajouteResultat(resultat);
        compteurUsage.increaseCompteur();
        return crypte.toString();
    }

    /**
     * Décrypte une chaîne cryptée par l'Analyseur.
     * Le format attendu est : chaque caractère suivi de ses positions d'apparition, séparés par des #.
     * Exemple : a#0#3#b#1#2#
     * Cette méthode reconstruit le texte original en plaçant chaque lettre à ses bonnes positions.
     *
     * @param texteCrypte le texte crypté sous forme de caractères et indices
     * @return le texte original reconstitué
     * @throws AssertionError si le texteCrypte est null ou si un bloc est vide
     */
    @Override
    public String decrypte(String texteCrypte) {
        assert texteCrypte != null : "Le texte crypté ne doit pas être null";
        double momentDebut = System.nanoTime();

        String[] blocs = texteCrypte.split("#");
        int taille = trouverTailleMax(blocs) + 1;
        char[] reconstruction = new char[taille];

        int i = 0;
        while (i < blocs.length - 1) {
            assert blocs[i].length() > 0 : "Bloc vide détecté";
            char lettre = blocs[i].charAt(0);
            i++;

            while (i < blocs.length && Character.isDigit(blocs[i].charAt(0))) {
                int index = Integer.parseInt(blocs[i]);
                reconstruction[index] = lettre;
                i++;
            }
        }
        Resultat resultat = new Resultat(this, LocalDateTime.now(), momentDebut, texteCrypte, new String(reconstruction));
        applicationUI.ajouteResultat(resultat);
        compteurUsage.increaseCompteur();
        return new String(reconstruction);
    }

    /**
     * Cherche le plus grand index dans les blocs pour dimensionner le tableau juste.
     */
    private int trouverTailleMax(String[] blocs) {
        int max = 0;
        for (String bloc : blocs) {
            if (!bloc.isEmpty() && Character.isDigit(bloc.charAt(0))) {
                int index = Integer.parseInt(bloc);
                if (index > max) {
                    max = index;
                }
            }
        }
        return max;
    }

    @Override
    public String getNom() {
        return "Analyseur";
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
        return " Analyseur ";
    }
}