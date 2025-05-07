/* Danielle Mafouo Fouodji */

package h25.msd.poo2.etu.io;

import h25.msd.poo2.echange.ApplicationUI;
import h25.msd.poo2.etu.io.exception.TP3FichierException;
import h25.msd.poo2.etu.utilisateur.AbstractUtilisateur;
import h25.msd.poo2.etu.utilisateur.Administrateur;
import h25.msd.poo2.etu.utilisateur.Utilisateur;


import java.io.*;

public class UtilisateurIO {
    ApplicationUI ui;

    public UtilisateurIO(ApplicationUI ui) {
        this.ui = ui;
    }

    public void sauvegardeUtilisateur(File fichier, AbstractUtilisateur abstractUtilisateur) throws TP3FichierException {
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        DataOutputStream dos = null;

        try {
            fos = new FileOutputStream(fichier);
            bos = new BufferedOutputStream(fos);
            dos = new DataOutputStream(bos);
            abstractUtilisateur.sauvegardeUtilisateur(dos);
        } catch (FileNotFoundException e) {
            throw new TP3FichierException("le fichier utilisateur n'a pas été trouvé", ui.getUtilisateur(), fichier);
        }
    }

    public AbstractUtilisateur chargeUtilisateur(File fichier) throws TP3FichierException {
        AbstractUtilisateur abstractUtilisateur = ui.getUtilisateur();
        String nomUtilisateur;
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        DataInputStream dis = null;

        try {
            fis = new FileInputStream(fichier);
            bis = new BufferedInputStream(fis);
            dis = new DataInputStream(bis);
            return AbstractUtilisateur.chargeUtilisateur(dis);
        } catch (FileNotFoundException e) {
            throw new TP3FichierException("le fichier utilisateur n'a pas été trouvé", ui.getUtilisateur(), fichier);
        } catch (RuntimeException e) {

            throw new TP3FichierException("Une erreur s'est produite à la lecture du fichier", ui.getUtilisateur(), fichier);
        }

    }

}
