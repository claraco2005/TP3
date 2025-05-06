package h25.msd.poo2.etu;



//**********************************************************
//   Remplacer cette classe par la votre (celle du tp2)    *
//**********************************************************


import h25.msd.poo2.echange.ApplicationModelI;
import h25.msd.poo2.echange.ApplicationUI;
import h25.msd.poo2.etu.io.GestionnaireFichiers;
import h25.msd.poo2.etu.recherche.MoteurRecherche;

public class Model implements ApplicationModelI {
    private GestionnaireFichiers gestionnaireDossiers;



    @Override
    public void initialise(ApplicationUI ui) {
        MoteurRecherche moteurRecherche = new MoteurRecherche();
        ui.setMoteurRecherche(moteurRecherche);
        GestionnaireFichiers gestionnaireFichiers= new GestionnaireFichiers();
        ui.setGestionnaireFichiers(gestionnaireFichiers);
    }


    @Override
    public String getNomsAuteurs() {
        return "Clara kabran  " +
                "Diarra Ndiaye  " +
                "Danielle Mafouo Fouodji";
    }


}
