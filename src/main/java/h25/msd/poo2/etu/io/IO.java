package h25.msd.poo2.etu.io;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import h25.msd.poo2.echange.AlgorithmeAvecParametreI;
import h25.msd.poo2.echange.AlgorithmeI;
import h25.msd.poo2.echange.ApplicationUI;
import h25.msd.poo2.etu.algo.DecalageCle;
import h25.msd.poo2.etu.algo.DecalageFixe;
import h25.msd.poo2.etu.algo.DecalageParametre;
import h25.msd.poo2.etu.algo.RotationParametre;
import h25.msd.poo2.etu.exception.TP3Exception;

import javax.management.MBeanAttributeInfo;
import java.io.File;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;


public class IO {
    private Map<String, String> propriete ;
    ApplicationUI ui;


    public IO(Map<String, String> propriete) {
        this.propriete = propriete;
    }

    public void sauvegardeAlgo(AlgorithmeI algo) throws TP3Exception {
       String nomAlgo = algo.getNom();
       String format = propriete.get("format-fichiers-parametres");
       String nomFichier = nomAlgo + "."+ format;
       String BASE_PATH= "parametres/";


            File fichier = new File(BASE_PATH + nomFichier);

            if (algo instanceof AlgorithmeAvecParametreI) {

                if (!fichier.exists()) {

                    if (format.equals("json")) {
                        ObjectMapper mapper = new ObjectMapper();

                        try {
                            mapper.writeValue(fichier, algo);

                        } catch (IOException e) {
                            throw new TP3Exception("Erreur le fichier json n'a pas été créer", ui.getUtilisateur());
                        }

                    } else if (format.equals("xml")) {
                        XmlMapper xmlMapper = new XmlMapper();

                        try {
                            xmlMapper.writeValue(fichier, algo);

                        } catch (IOException e) {
                            throw new TP3Exception("Erreur le fichier xml n'a pas été créer", ui.getUtilisateur());
                        }
                    }
                }
            }



    }

    public AlgorithmeI chargeAlgo(String nomAlgoRecherche) {


        return null;
    }

    public static void main(String[] args) {
        Map<String, String> propriete = new HashMap<>();
        propriete.put("format-fichiers-parametres", "json");
        IO io = new IO(propriete);
//        io.sauvegardeAlgo(new RotationParametre());
//        io.sauvegardeAlgo(new DecalageCle());
//        io.sauvegardeAlgo(new DecalageParametre());
//        io.sauvegardeAlgo(new DecalageFixe());
    }
}