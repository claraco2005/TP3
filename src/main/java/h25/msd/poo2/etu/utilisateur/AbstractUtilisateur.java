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


    @Override
    public String toString() {
        return "AbstractUtilisateur{" +
                "nom='" + nom + '\'' +
                '}';
    }
}
