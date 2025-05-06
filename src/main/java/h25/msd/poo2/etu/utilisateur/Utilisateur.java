package h25.msd.poo2.etu.utilisateur;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

public class Utilisateur extends AbstractUtilisateur {
    private int nombreUsages = 0; //encryption et decryption

    public Utilisateur(String nom) {
        super(nom);
    }

    @Override
    public void sauvegardeUtilisateur(DataOutputStream dos) {
        try {
            dos.writeUTF("utilisateur");
            dos.writeUTF(getNom());
            dos.writeInt(nombreUsages);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            if (dos!=null)
                try {
                    dos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
        }
    }
    public static AbstractUtilisateur chargeUtilisateur(DataInputStream dis) {
        Utilisateur utilisateur;
        try {
            String nomUtilisateur = dis.readUTF();
            int nombreUsage = dis.readInt();
          utilisateur = new Utilisateur(nomUtilisateur);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            if (dis!=null)
                try {
                    dis.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
        }
        return utilisateur;
    }
    public int getNombreUsages() {
        return nombreUsages;
    }
}
