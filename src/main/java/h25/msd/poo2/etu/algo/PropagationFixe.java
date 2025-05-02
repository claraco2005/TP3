package h25.msd.poo2.etu.algo;

import h25.msd.poo2.echange.AlgorithmeI;
import h25.msd.poo2.echange.ApplicationUI;
import h25.msd.poo2.etu.classeDelegue.CompteurUsage;

import java.time.LocalDateTime;
import java.util.List;

// Mame Diarra Bousso Ndiaye
public class PropagationFixe extends AbstractAlgoCryptographique implements AlgorithmeI {

    private ApplicationUI applicationUI;
    private CompteurUsage compteurUsage;

    public PropagationFixe() {
        produitCaractereSet();
        this.compteurUsage = new CompteurUsage();
    }


    /**
     * encrypte une chaîne en comparant 2 valeurs successives dans la chaîne, puis en additionnant le second au premier,
     * puis le 3e au second et ainsi de suite jusqu’au dernier caractère. On ne modifie pas le dernier caractère.
     *
     * @param texteLisible chaine à crypter
     * @return texteCryptee
     */
    @Override
    public String encrypte(String texteLisible) {
        assert texteLisible != null && !texteLisible.isEmpty() : "le texte a crypte ne peut pas etre null ou vide";

        String texteCrypte = "";
        double momentDebut = System.nanoTime();
        List<Integer> listeNombre = traduitTexteEnListe(texteLisible);

        for (int i = 0; i < listeNombre.size() - 1; i++) {
            int somme = listeNombre.get(i) + listeNombre.get(i + 1);  //ex on ajoute le second(i+1) au premier (i)

            somme = somme % getCharMap().size();   // pour gerer les debordements

            listeNombre.set(i, somme);
        }

        texteCrypte = traduitListeEnTexte(listeNombre);
        Resultat resultat = new Resultat(this, LocalDateTime.now(), momentDebut, texteLisible, texteCrypte);
        applicationUI.ajouteResultat(resultat);
        compteurUsage.increaseCompteur();
        return texteCrypte;
    }

    /**
     * on doit partir de la fin et revenir vers le début en faisant le processus inverse de l'encryptage
     *
     * @param texteEncrypte chaine à crypter
     * @return texteDecrypte
     */
    @Override
    public String decrypte(String texteEncrypte) {
        double momentDebut = System.nanoTime();
        assert texteEncrypte != null && !texteEncrypte.isEmpty() : "le texte a crypte ne peut pas etre null ou vide";
        String texteDecrypte = "";
        List<Integer> listeNombre = traduitTexteEnListe(texteEncrypte);

        for (int i = listeNombre.size() - 2; i >= 0; i--) {          // on commence avec l'avant dernier element jusqu'au premier element
            int valeurDecrypte = (listeNombre.get(i) - listeNombre.get(i + 1)) % getCharMap().size();   //exemple lors de l'encryptage a = a+b ici on fait linverse en faisant -b et le modulo pour gerer le debordement
            if (valeurDecrypte < 0) {  // pour gerer les valeurs negatifs
                valeurDecrypte += getCharMap().size();
            }
            listeNombre.set(i, valeurDecrypte);
        }
        texteDecrypte = traduitListeEnTexte(listeNombre);
        Resultat resultat = new Resultat(this, LocalDateTime.now(), momentDebut, texteEncrypte, texteDecrypte);
        applicationUI.ajouteResultat(resultat);
        compteurUsage.increaseCompteur();
        return texteDecrypte;
    }

    @Override
    public String getNom() {
        return "PropagationFixe";
    }

    @Override
    public int getNombreUsages() {
        return compteurUsage.getCompteur();
    }

    @Override
    public void setApplicationUI(ApplicationUI tp2Controller) {
        applicationUI = tp2Controller;
    }


    @Override
    public String toString() {
        return " Propagation Fixe ";
    }
}
