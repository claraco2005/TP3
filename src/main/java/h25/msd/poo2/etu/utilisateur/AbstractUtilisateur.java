
/* Danielle Mafouo Fouodji */

package h25.msd.poo2.etu.utilisateur;

import java.io.*;

public abstract class AbstractUtilisateur {
    private String nom;

    public AbstractUtilisateur(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public abstract void sauvegardeUtilisateur(DataOutputStream dos);


    public static AbstractUtilisateur chargeUtilisateur(DataInputStream dis) {
        AbstractUtilisateur abstractUtilisateur = null;
        try {
            String type = dis.readUTF();
            switch (type) {
                case "invite":
                    abstractUtilisateur = Invite.chargeUtilisateur(dis);
                    break;
                case "utilisateur":
                    abstractUtilisateur = Utilisateur.chargeUtilisateur(dis);
                    break;
                case "administrateur":
                    abstractUtilisateur = Administrateur.chargeUtilisateur(dis);
                    break;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return abstractUtilisateur;


    }

    @Override
    public String toString() {
        return "AbstractUtilisateur{" +
                "nom='" + nom + '\'' +
                '}';
    }
}
