/* Clara ,Diarra,Danielle*/

package h25.msd.poo2.etu.io;

import h25.msd.poo2.echange.AlgorithmeI;
import h25.msd.poo2.echange.ApplicationUI;
import h25.msd.poo2.echange.Dossiers;
import h25.msd.poo2.echange.GestionnaireFichierI;
import h25.msd.poo2.etu.exception.TP3Exception;
import h25.msd.poo2.etu.io.exception.TP3FichierException;
import h25.msd.poo2.etu.utilisateur.AbstractUtilisateur;

import java.io.File;


public class GestionnaireFichiers implements GestionnaireFichierI {
    ApplicationUI ui;
    UtilisateurIO utilisateurIO;
    File fichiers = new File("./");

    public GestionnaireFichiers(ApplicationUI ui) {
        utilisateurIO = new UtilisateurIO(ui);
        this.ui = ui;
    }

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
        File dossierUtilisateur = new File("utilisateurs/utilisateur.uti");
        if (dossierUtilisateur.isFile())
            dossierUtilisateur.delete();

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

        try {
            utilisateurIO.sauvegardeUtilisateur(new File("utilisateurs/utilisateur.uti"), abstractUtilisateur);
        } catch (TP3FichierException e) {
            throw new TP3Exception("Cher(e) " + e.getUtilisateur().getNom() + " " + e.getMessage() + "\n avec le fichier " + e.getFile().getName(), e.getUtilisateur());
        }
    }

    @Override
    public AbstractUtilisateur chargeUtilisateur() throws TP3Exception {
        try {
            return utilisateurIO.chargeUtilisateur(new File("utilisateurs/utilisateur.uti"));
        } catch (TP3FichierException e) {
            throw new TP3Exception("Cher(e) " + e.getUtilisateur().getNom() + " " + e.getMessage() + "\n avec le fichier " + e.getFile().getName(), e.getUtilisateur());
        }
    }

}
