package h25.msd.poo2.etu.algo;

import h25.msd.poo2.echange.AlgorithmeI;
import h25.msd.poo2.echange.ApplicationUI;
import h25.msd.poo2.etu.classeDelegue.CompteurUsage;

import java.time.LocalDateTime;
import java.util.List;

public class DecalageFixe extends AbstractAlgoCryptographique implements AlgorithmeI {

    private CompteurUsage compteurUsage;
    private final int DECALAGE = 4;
    private ApplicationUI applicationUI;

    public DecalageFixe() {
        this.compteurUsage = new CompteurUsage();
    }

    /**
     * Encrypte la chaine de caractere recu en parametre en utilisant une cle qui est un mombre fixe preetabli qu'on ajoutera a chaque valeur de caractere
     *
     * @param texteLisible la chaine a encrypter
     * @return la chaine cryptee
     */

    @Override
    public String encrypte(String texteLisible) {

        double momentDebut = System.nanoTime();
        assert texteLisible != null : "Le texte à encrypter est null";
        String chaineCryptee = "";
        List<Integer> listeNombre = traduitTexteEnListe(texteLisible);
        for (int i = 0; i < listeNombre.size(); i++) {
            int nombre = listeNombre.get(i);
            nombre = (nombre + DECALAGE) % getCharMap().size();
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

        String chaineDeCryptee = "";
        assert texteEncrypte != null : "Le texte à decrypter est null";
        double momentDebut = System.nanoTime();


        List<Integer> listeLettre = traduitTexteEnListe(texteEncrypte);

        for (int i = 0; i < listeLettre.size(); i++) {
            int nombre = listeLettre.get(i);
            nombre = nombre - DECALAGE;
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
        return " DecalageFixe";
    }
}

