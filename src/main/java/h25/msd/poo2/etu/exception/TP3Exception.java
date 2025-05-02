package h25.msd.poo2.etu.exception;

import h25.msd.poo2.etu.utilisateur.AbstractUtilisateur;

public class TP3Exception extends Exception {
    private AbstractUtilisateur utilisateur;

    public TP3Exception(String message, AbstractUtilisateur utilisateur) {
        super(message);
        this.utilisateur = utilisateur;
    }

    public AbstractUtilisateur getUtilisateur() {
        return utilisateur;
    }

}
