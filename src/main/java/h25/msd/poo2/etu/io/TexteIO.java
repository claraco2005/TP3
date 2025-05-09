/*CLARA KABRAN*/
package h25.msd.poo2.etu.io;

import h25.msd.poo2.etu.io.exception.TP3FichierException;
import h25.msd.poo2.etu.utilisateur.AbstractUtilisateur;
import h25.msd.poo2.etu.utilisateur.Administrateur;
import h25.msd.poo2.etu.utilisateur.Utilisateur;

import java.io.*;
import java.util.HashMap;
import java.util.Map;


public class TexteIO {

    AbstractUtilisateur utilisateur;


    public TexteIO(AbstractUtilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    /**
     * Enregistre un fichier texte ligne par ligne en respectant le format de ligne utilisé par le système
     * d’exploitation.
     * o La méthode reçoit en paramètres :
     * ▪ Le fichier qui doit être mis sur le disque
     * ▪ Le texte qui doit aller dans le fichier
     */

    public void enregistreTexte(File fichier, String texte) throws TP3FichierException {


        FileWriter fw = null;
        BufferedWriter bw = null;


        try {
            fw = new FileWriter(fichier);
            bw = new BufferedWriter(fw);
            String[] lignes = texte.split("\n");

            for (String ligne : lignes) {
                bw.write(ligne);
                bw.newLine();
            }

        } catch (IOException e) {
            throw new TP3FichierException("Erreur lors de l'écriture", utilisateur, fichier);
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    /**
     * Charge un fichier texte standard complet dans une chaîne de caractères
     * La méthode reçoit en paramètres :
     * ▪ Le fichier qui contient le texte à charger.
     *
     * @param fichier
     * @return
     */

    public String chargeText(File fichier) throws TP3FichierException {

        FileReader fr = null;
        BufferedReader br = null;

        StringBuilder sb = new StringBuilder();


        try {
            fr = new FileReader(fichier);
            br = new BufferedReader(fr);

            String ChaineDeCaractereObtenu;
            while ((ChaineDeCaractereObtenu = br.readLine()) != null) {
                sb.append(ChaineDeCaractereObtenu).append(System.lineSeparator());
            }
        } catch (IOException e) {
            throw new TP3FichierException("Erreur lors de la lecture", utilisateur, fichier);
        }

        return sb.toString();
    }


    public Map<String, String> chargeRessource() throws TP3FichierException {

        Map<String, String> map = new HashMap<>();
        File fichier = new File("configuration.txt");
        FileReader fr = null;
        BufferedReader br = null;

        try {

            fr = new FileReader(fichier);
            br = new BufferedReader(fr);
            String ligne;
            while ((ligne = br.readLine()) != null) {
                if (ligne.contains(":")) {
                    String[] parts = ligne.split(":", 2);
                    map.put(parts[0].trim(), parts[1].trim());
                }
            }
        } catch (IOException e) {
            throw new TP3FichierException("Erreur lors de la lecture de configuration.txt", utilisateur, fichier);
        }
        return map;
    }


    public static void main(String[] args) {
//        TexteIO tio = new TexteIO();
//        File fichier = new File("C://Users//2358353//OneDrive - Cégep Limoilou//Bureau//clara.txt");
//        String texte = "Je m'appelle clara \n je suis au cegep limoilou \n en 3e année";
//
//        tio.enregistreTexte(fichier, texte);


        // Création d’un utilisateur fictif (remplace par un vrai admin si tu veux)
//        Utilisateur utilisateur = new Utilisateur("Clara");
//
//        // Création de TexteIO avec l'utilisateur
//        TexteIO texteIO = new TexteIO(utilisateur);
//
//        try {
//            // Appel de chargeRessource
//            Map<String, String> config = texteIO.chargeRessource();
//
//            // Affiche chaque clé/valeur
//            System.out.println("Contenu de configuration.txt :");
//            for (Map.Entry<String, String> entry : config.entrySet()) {
//                System.out.println(entry.getKey() + " = " + entry.getValue());
//            }
//
//        } catch (TP3FichierException e) {
//            System.err.println("Erreur capturée : " + e.getMessage());
//        }
//    }
//

    }
}