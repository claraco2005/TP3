/* Danielle Mafouo Fouodji*/
package h25.msd.poo2.etu.algo;

import h25.msd.poo2.echange.AlgorithmeI;
import h25.msd.poo2.echange.ResultatI;

import java.time.LocalDateTime;

public class Resultat implements ResultatI {

    private AlgorithmeI algoUtiliser;
    private LocalDateTime momentEncryption;
    private double dureeEncryption;
    private String texteOriginal;
    private String texteEncrypte;

    public Resultat(AlgorithmeI algoUtilise, LocalDateTime momentEncryption, double momentDebut, String texteOriginal, String texteEncrypte) {
        this.algoUtiliser = algoUtilise;
        this.momentEncryption = momentEncryption;
        this.dureeEncryption = System.nanoTime() - momentDebut;
        this.texteOriginal = texteOriginal;
        this.texteEncrypte = texteEncrypte;
    }

    @Override
    public String toString() {
        return momentEncryption + "; " + algoUtiliser.getNom() + "; duree: " + dureeEncryption + " ns";
    }

    @Override
    public AlgorithmeI getAlgoUtilise() {
        return algoUtiliser;
    }

    @Override
    public LocalDateTime getQuand() {
        return momentEncryption;
    }

    @Override
    public double getDureeTraitement() {
        return dureeEncryption;
    }

    @Override
    public String getTexteOriginal() {
        return texteOriginal;
    }

    @Override
    public String getTexteFinal() {
        return texteEncrypte;
    }

    @Override
    public int compareTo(ResultatI o) {
        return 0;
    }
}
