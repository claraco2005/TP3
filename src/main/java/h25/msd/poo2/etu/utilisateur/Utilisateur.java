package h25.msd.poo2.etu.utilisateur;

import java.io.DataOutputStream;
import java.io.IOException;

public class Utilisateur extends AbstractUtilisateur {
    private int nombreUsages = 0; //encryption et decryption

    public Utilisateur(String nom) {
        super(nom);
    }


}
