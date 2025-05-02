/* Clara S Kabran */

package h25.msd.poo2.etu.algo;

import h25.msd.poo2.echange.AlgorithmeAvecParametreI;
import h25.msd.poo2.echange.ApplicationUI;
import h25.msd.poo2.etu.classeDelegue.CompteurUsage;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe représentant l'algorithme DécalageParametre.
 * Ce dernier chiffre et déchiffre un texte en ajoutant un décalage fixe à chaque caractère.
 * Le décalage est paramétrable par l'utilisateur et limité à une valeur maximale de 100.
 */
public class DecalageParametre extends AbstractAlgoCryptographique implements AlgorithmeAvecParametreI {

    private CompteurUsage compteurUsage;
    private String decalage = "0";  // Définir 0 par défaut
    private ApplicationUI applicationUI;
    private int decalageInt = 0;


    public DecalageParametre() {
        this.compteurUsage = new CompteurUsage();
    }

    /**
     * Chiffre un texte lisible en appliquant un décalage fixe.
     */
    @Override
    public String encrypte(String texteLisible) {
        String chaineCryptee = "";

        double momentDebut = System.nanoTime();
        if (decalage != null && !decalage.isEmpty()) {
            decalageInt = Integer.parseInt(decalage);
        }
        assert decalageInt >= 0 && decalageInt <= 70 : "Décalage hors limites";

        List<Integer> listeNombre = traduitTexteEnListe(texteLisible);

        for (int i = 0; i < listeNombre.size(); i++) {
            int val = (listeNombre.get(i) + decalageInt) % getCharMap().size();
            listeNombre.set(i, val);
        }
        chaineCryptee = traduitListeEnTexte(listeNombre);

        Resultat resultat = new Resultat(this, LocalDateTime.now(), momentDebut, texteLisible, chaineCryptee);
        applicationUI.ajouteResultat(resultat);
        compteurUsage.increaseCompteur();
        return chaineCryptee;
    }

    /**
     * Déchiffre un texte crypté en appliquant le décalage inverse.
     */
    @Override
    public String decrypte(String texteCrypte) {
        String chaineDeCryptee = "";

        double momentDebut = System.nanoTime();
        if (decalage != null && !decalage.isEmpty()) {
            decalageInt = Integer.parseInt(decalage);
        }

        assert decalageInt >= 0 && decalageInt <= 70 : "Décalage hors limites";

        List<Integer> listeNombre = traduitTexteEnListe(texteCrypte);
        for (int i = 0; i < listeNombre.size(); i++) {
            int val = (listeNombre.get(i) - decalageInt + getCharMap().size()) % getCharMap().size();
            listeNombre.set(i, val);
        }
        chaineDeCryptee = traduitListeEnTexte(listeNombre);
        Resultat resultat = new Resultat(this, LocalDateTime.now(), momentDebut, texteCrypte, chaineDeCryptee);
        applicationUI.ajouteResultat(resultat);
        compteurUsage.increaseCompteur();
        return chaineDeCryptee;
    }

    @Override
    public String getNom() {
        return "Décalage Paramètre";
    }

    @Override
    public int getNombreUsages() {
        return compteurUsage.getCompteur();
    }

    @Override
    public void setParametre(String nom, String valeur) {
        decalage = valeur;
    }

    @Override
    public String getParametre(String nom) {
        String valeur;
        valeur = getParametres().get(nom);
        return valeur;
    }

    /**
     * Valide que le décalage est un nombre entre 0 et 70.
     */
    @Override
    public String valideParametre(String nom, String valeur) {
        String messageErreur = "";

        if (!valeur.matches("[0-9]+")) {
            messageErreur = "Le décalage doit être un nombre.";
        }
        if (messageErreur.equals("") && Integer.parseInt(valeur) > 70) {
            messageErreur = "Le décalage doit être inférieur ou égal à 70.";
        }

        return messageErreur;
    }

    @Override
    public Map<String, String> getParametres() {
        Map<String, String> parametres = new HashMap<>();
        parametres.put("decalage", decalage);
        return parametres;
    }

    @Override
    public void setApplicationUI(ApplicationUI tp2Controller) {
        applicationUI = tp2Controller;
    }

    @Override
    public String toString() {
        return "Décalage Paramètre";
    }


}