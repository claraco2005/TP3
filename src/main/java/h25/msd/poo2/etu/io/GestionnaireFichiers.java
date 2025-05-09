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
    TexteIO texteIO;
    File fichiers = new File("./");

        // a averifier
    private File dossierEncryption = new File("encryptions");
    private File dossierOriginaux = new File("texte-originaux");
    private File dossierParametres = new File("parametres");
    private File dossierUtilisateur = new File("utilisateurs");

    public GestionnaireFichiers(ApplicationUI ui) {
        utilisateurIO = new UtilisateurIO(ui);
        texteIO = new TexteIO();
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

    /***
     * Enregistre le texte reçu en paramètre dans le fichier reçu en paramètre à l’aide de la classe
     * TexteIO.
     * @param fichier
     * @param texte
     * @throws TP3Exception
     */
    @Override
    public void enregistreTexte(File fichier, String texte) throws TP3Exception {

        try {
            texteIO.enregistreTexte(fichier, texte);
        } catch (TP3FichierException e) {
            throw new TP3Exception("Cher(e) " + e.getUtilisateur().getNom() + "Il s'est produit une" + e.getMessage() + "\n avec le fichier " + e.getFile().getName(), e.getUtilisateur()); // demander au prof sinon le nom de l,utilisateur va apparaitre encore
        }

    }


    @Override
    public String chargeTexte(File fichier) throws TP3Exception {

        try {
            return texteIO.chargeText(fichier);
        } catch (TP3FichierException e) {
            throw new TP3Exception("Cher(e) " + e.getUtilisateur().getNom() + "Il s'est produit une" + e.getMessage() + "\n avec le fichier " + e.getFile().getName(), e.getUtilisateur()); // demander au prof sinon le nom de l,utilisateur va apparaitre encore
        }


    }


    @Override
    public File getDossier(Dossiers dossier) {

        File fichier = null ;

        switch (dossier){
            case ENCRYPTIONS :
                fichier = dossierEncryption;
            case PARAMETRES:
                fichier = dossierParametres;
            case UTILISATEURS:
                fichier = dossierUtilisateur;

        }
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
