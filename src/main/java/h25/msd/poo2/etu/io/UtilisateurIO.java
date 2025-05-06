/* Danielle Mafouo Fouodji */

package h25.msd.poo2.etu.io;

import h25.msd.poo2.etu.utilisateur.AbstractUtilisateur;
import h25.msd.poo2.etu.utilisateur.Administrateur;
import h25.msd.poo2.etu.utilisateur.Utilisateur;


import java.io.*;

public class UtilisateurIO {


    public void sauvegardeUtilisateur(AbstractUtilisateur abstractUtilisateur) {
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        DataOutputStream dos = null;

        try {
            fos = new FileOutputStream("utilisateur.uti");
            bos = new BufferedOutputStream(fos);
            dos = new DataOutputStream(bos);
            abstractUtilisateur.sauvegardeUtilisateur(dos);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public AbstractUtilisateur chargeUtilisateur(File fichier) {
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
            throw new RuntimeException(e);
        }


    }
}
