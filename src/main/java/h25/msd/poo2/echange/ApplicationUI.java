package h25.msd.poo2.echange;

import h25.msd.poo2.etu.utilisateur.AbstractUtilisateur;

public interface ApplicationUI {
    void ajouteResultat(ResultatI resultat);
    public void setMoteurRecherche(MoteurRechercheI gestionnaire);
    public void setGestionnaireFichiers(GestionnaireFichierI gestionnaire);
    public void informeUtilisateur(String message);
    public AbstractUtilisateur getUtilisateur();
}
