/* Clara ,Diarra,Danielle*/
package h25.msd.poo2.etu.io;

import h25.msd.poo2.echange.AlgorithmeI;
import h25.msd.poo2.echange.Dossiers;
import h25.msd.poo2.echange.GestionnaireFichierI;
import h25.msd.poo2.etu.exception.TP3Exception;
import h25.msd.poo2.etu.utilisateur.AbstractUtilisateur;

import java.io.File;


public class GestionnaireFichiers implements GestionnaireFichierI {
    UtilisateurIO utilisateurIO = new UtilisateurIO();
    File fichiers = new File("./");

    @Override
    public void prepareDossiersRequis() {
        if (!fichiers.exists()) {
            fichiers.mkdir();
        }
        File fichierUtilisateur = new File("utilisateurs");
        if (!fichierUtilisateur.exists()) {
            fichierUtilisateur.mkdir();
        }
    }

    @Override
    public void viderDossiersFichiers() {

    }

    @Override
    public void enregistreTexte(File fichier, String texte) throws TP3Exception {

    }

    @Override
    public String chargeTexte(File fichier) throws TP3Exception {
        return "";
    }

    @Override
    public File getDossier(Dossiers dossier) {
        return null;
    }

    @Override
    public void sauvegarderParametreSelectionne(AlgorithmeI algo) throws TP3Exception {

    }

    @Override
    public void chargeParametresDansAlgo(AlgorithmeI algoRecherche) throws TP3Exception {

    }

    @Override
    public void sauvegardeUtilisateur(AbstractUtilisateur abstractUtilisateur) throws TP3Exception {

        utilisateurIO.sauvegardeUtilisateur(abstractUtilisateur);
    }

    @Override
    public AbstractUtilisateur chargeUtilisateur() throws TP3Exception {
        return utilisateurIO.chargeUtilisateur(new File("utilisateurs"));
    }
}
