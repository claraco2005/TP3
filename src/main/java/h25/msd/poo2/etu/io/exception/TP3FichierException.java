package h25.msd.poo2.etu.io.exception;

import h25.msd.poo2.etu.exception.TP3Exception;
import h25.msd.poo2.etu.utilisateur.AbstractUtilisateur;

import java.io.File;

public class TP3FichierException extends TP3Exception {


    // a verifier mais je pense c'est correct

    File file;

    public TP3FichierException(String message, AbstractUtilisateur utilisateur , File file ) {
        super(message, utilisateur);
        this.file = file;

    }

    public File getFile() {
        return file;
    }
}
