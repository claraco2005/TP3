package h25.msd.poo2.echange;

import h25.msd.poo2.etu.exception.TP3Exception;
import h25.msd.poo2.etu.utilisateur.AbstractUtilisateur;

import java.io.File;

public interface GestionnaireFichierI {

    /**
     * Crée les dossiers requis pour l'application dans le dossier de travail s'ils n'existent pas déjà.
     */
    void prepareDossiersRequis();

    void viderDossiersFichiers();

    void enregistreTexte(File fichier, String texte) throws TP3Exception;

    String chargeTexte(File fichier) throws TP3Exception;

    File getDossier(Dossiers dossier);

    void sauvegarderParametreSelectionne(AlgorithmeI algo) throws TP3Exception;

    void chargeParametresDansAlgo(AlgorithmeI algoRecherche) throws TP3Exception;

    void sauvegardeUtilisateur(AbstractUtilisateur abstractUtilisateur) throws TP3Exception;

    AbstractUtilisateur chargeUtilisateur() throws TP3Exception;
}
