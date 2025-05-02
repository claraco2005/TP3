
/*Danielle Mafouo Fouodji*/
package h25.msd.poo2.etu.algo;

/* Ma colegue Mame Diarra a fait une erreur de manipulation en faisant un commit ce qui justifie son nom dans la classe.
Cependant c'est Daniellle Mafouo Fouodji qui a fait cette classe */


import h25.msd.poo2.echange.ApplicationUI;
import h25.msd.poo2.etu.classeDelegue.CompteurUsage;

import java.time.LocalDateTime;
import java.util.List;

public class DecalageAvecInversion extends AbstractAlgoCryptographique {

    private CompteurUsage compteurUsage = new CompteurUsage();
    private ApplicationUI applicationUI;


    /**
     * Inverse une chaine de caractere recu en parametre
     *
     * @param mot la chaine a inverser
     * @return la chaine inversee
     */

    public String inverser(String mot) {
        assert mot != null : "Le mot a inverser est null";
        String motInverse = "";
        for (int i = mot.length() - 1; i >= 0; i--) {
            motInverse += mot.charAt(i);
        }
        return motInverse;
    }

    /**
     * inverse une chaine de caractere puis encrypte la chaine inversee en ajoutant 1 au caractere 1 et en incrementant de 1 a chaque caractere suivant
     *
     * @param texteLisible le texte a inverser
     * @return la chaine cryptee
     */
    @Override
    public String encrypte(String texteLisible) {
        assert texteLisible != null : "Le texte à encrypter est null";
        compteurUsage.increaseCompteur();
        double momentDebut = System.nanoTime();
        String chaineCryptee = "";
        String motInverse = inverser(texteLisible);

        List<Integer> listeNombre = traduitTexteEnListe(motInverse);
        for (int i = 0; i < listeNombre.size(); i++) {
            int nombre = listeNombre.get(i);
            nombre = (nombre + i + 1) % getCharMap().size();
            listeNombre.set(i, nombre);
        }
        chaineCryptee = traduitListeEnTexte(listeNombre);
        Resultat resultat = new Resultat(this, LocalDateTime.now(), momentDebut, texteLisible, chaineCryptee);
        applicationUI.ajouteResultat(resultat);

        return chaineCryptee;
    }

    /**
     * decrypte une chaine de caracteres  en retirant 1 au caractere 1 et en decrementant de 1 a chaque caractere suivant puis inverse la chaine de caractere obtenue
     *
     * @param texteEncrypte le texte a decripter
     * @return la chaine decryptee
     */
    @Override
    public String decrypte(String texteEncrypte) {
        assert texteEncrypte != null : "Le texte à décrypter est null";
        double momentDebut = System.nanoTime();
        String chaineDeCryptee = "";
        List<Integer> listeLettre = traduitTexteEnListe(texteEncrypte);
        for (int i = 0; i < listeLettre.size(); i++) {
            int nombre = listeLettre.get(i);
            nombre = (nombre - i - 1);
            while (nombre < 0) {
                nombre = nombre + getCharMap().size();
            }
            listeLettre.set(i, nombre);

        }
        chaineDeCryptee = traduitListeEnTexte(listeLettre);
        String chaine = inverser(chaineDeCryptee);
        Resultat resultat = new Resultat(this, LocalDateTime.now(), momentDebut, texteEncrypte, chaineDeCryptee);
        applicationUI.ajouteResultat(resultat);
        compteurUsage.increaseCompteur();
        return chaine;
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
        return " DecalageAvecInversion ";
    }


}

