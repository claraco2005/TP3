package h25.msd.poo2.etu.utilisateur;

import java.io.DataOutputStream;
import java.io.IOException;

public class Administrateur extends Utilisateur {
    private int nombreFichierGenere;


    public Administrateur(String nom) {
        super( nom);
    }

    public int getNombreFichierGenere() {
        return nombreFichierGenere;
    }

    public void setNombreFichierGenere(int nombreFichierGenere) {
        this.nombreFichierGenere = nombreFichierGenere;
    }

}