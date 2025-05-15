/* Clara ,Diarra,Danielle*/

package h25.msd.poo2.etu.io;

import h25.msd.poo2.echange.*;
import h25.msd.poo2.etu.exception.TP3Exception;
import h25.msd.poo2.etu.io.exception.TP3FichierException;
import h25.msd.poo2.etu.utilisateur.AbstractUtilisateur;

import java.io.File;
import java.util.Map;


public class GestionnaireFichiers implements GestionnaireFichierI {
    ApplicationUI ui;
    UtilisateurIO utilisateurIO;
    TexteIO texteIO;
    File fichiers = new File("./");
    private IO io;
    Map<String, String> propriete;



    private File dossierEncryption = new File("encryptions");
    private File dossierOriginaux = new File("texte-originaux");
    private File dossierParametres = new File("parametres");
    private File dossierUtilisateur = new File("utilisateurs");
    private  File[] listeDossier = {dossierEncryption, dossierOriginaux, dossierParametres, dossierUtilisateur};

    public GestionnaireFichiers(ApplicationUI ui) {
        utilisateurIO = new UtilisateurIO(ui);
        texteIO = new TexteIO();
        try {
            this.propriete = texteIO.chargeRessource();
        } catch (TP3FichierException e) {
            throw new RuntimeException(e);
        }
        this.ui = ui;
        this.io = new IO(propriete);

    }

    @Override
    public void prepareDossiersRequis() {
        for(File dossier : listeDossier) {
            if(!dossier.exists()){
                dossier.mkdir();
            }
        }
        if (!fichiers.exists()) {
            fichiers.mkdir();
        }
        //File fichierUtilisateur = new File("utilisateurs");
        // if (!dossierUtilisateur.exists()) {
        //    dossierUtilisateur.mkdir();
        // }
       // File dossierParametres = new File("parametres");
    // if (!dossierParametres.exists()) {
    //     dossierParametres.mkdir();
    }


    @Override
    public void viderDossiersFichiers() {
            for(File dossier : listeDossier){
                    File[] fichier = dossier.listFiles();
                if(fichier  != null){
                    for(File f : fichier){
                        f.delete();
                    }
                }
            }
        //viderContenuDossier(dossierParametres);
        // viderContenuDossier(dossierUtilisateur);
        //viderContenuDossier(dossierEncryption);
        //viderContenuDossier(dossierOriginaux);
    }

    /***
     * Supprime tous les fichiers qui sont dans le dossiers passé en parametre
     * @param dossier le dossier a vider
     */
    private void viderContenuDossier(File dossier){
        if(dossier.exists() && dossier.isDirectory()){
            File[] fichiers = dossier.listFiles();
            if(fichiers  != null){
                for(File f : fichiers){
                    f.delete();
                }
            }
        }
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
        return new File("fichiers/"+dossier.name());
    }

    @Override
    public void sauvegarderParametreSelectionne(AlgorithmeI algo) throws TP3Exception {
        try{
        io.sauvegardeAlgo(algo);
        }catch (TP3FichierException t){
            throw new TP3Exception("Cher(e) " + t.getUtilisateur().getNom() +"Il s'est produit une" + t.getMessage() + "\n avec le fichier " + t.getFile().getName(), t.getUtilisateur());
        }
    }

    @Override
    public void chargeParametresDansAlgo(AlgorithmeI algoRecherche) throws TP3Exception {
        try{
            io.chargeAlgo(algoRecherche.getNom());
        }catch (TP3FichierException t){
            throw new TP3Exception("Cher(e) " + t.getUtilisateur().getNom() +"Il s'est produit une" + t.getMessage() + "\n avec le fichier " + t.getFile().getName(), t.getUtilisateur());
        }
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
