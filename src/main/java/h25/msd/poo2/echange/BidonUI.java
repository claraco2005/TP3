package h25.msd.poo2.echange;

import h25.msd.poo2.etu.utilisateur.AbstractUtilisateur;
import h25.msd.poo2.etu.utilisateur.Invite;

public  class BidonUI implements ApplicationUI {


    @Override
    public void ajouteResultat(ResultatI resultat) {
        // ne fait rien
    }

    @Override
    public void setMoteurRecherche(MoteurRechercheI gestionnaire) {
        //rien
    }

    @Override
    public void setGestionnaireFichiers(GestionnaireFichierI gestionnaire) {
        //rien
    }

    @Override
    public void informeUtilisateur(String message) {
        //rien
    }

    @Override
    public AbstractUtilisateur getUtilisateur() {
        return new Invite();
    }
}
