/* Danielle Mafouo Fouodji */
package h25.msd.poo2.etu.utilisateur;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Administrateur extends Utilisateur {
    private int nombreFichierGenere;


    public Administrateur(String nom) {
        super(nom);
    }

    public int getNombreFichierGenere() {
        return nombreFichierGenere;
    }

    public void setNombreFichierGenere(int nombreFichierGenere) {
        this.nombreFichierGenere = nombreFichierGenere;
    }

    @Override
    public void sauvegardeUtilisateur(DataOutputStream dos) {
        try {
            dos.writeUTF("administrateur");
            dos.writeUTF(getNom());
            dos.writeInt(getNombreUsages());
            dos.writeInt(nombreFichierGenere);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (dos != null)
                try {
                    dos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
        }
    }

    public static AbstractUtilisateur chargeUtilisateur(DataInputStream dis) {
        Administrateur administrateur;

        try {
            String nomAdministrateur = dis.readUTF();
            int nombreUsage = dis.readInt();
            int nombreFichiers = dis.readInt();
            administrateur = new Administrateur(nomAdministrateur);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (dis != null)
                try {
                    dis.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
        }
        return administrateur;
    }

}