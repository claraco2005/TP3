package h25.msd.poo2.etu.io;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import h25.msd.poo2.echange.AlgorithmeAvecParametreI;
import h25.msd.poo2.echange.AlgorithmeI;
import h25.msd.poo2.etu.algo.DecalageCle;
import h25.msd.poo2.etu.algo.DecalageParametre;
import h25.msd.poo2.etu.algo.RotationParametre;

import javax.management.MBeanAttributeInfo;
import java.io.File;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;


public class IO {
    private Map<String, String> propriete ;

    public IO(Map<String, String> propriete) {
        this.propriete = propriete;
    }

    public void sauvegardeAlgo(AlgorithmeI algo) {
       String nomAlgo = algo.getNom();
       String format = propriete.getOrDefault("format-fichiers-parametres", "json");
       String nomFichier = nomAlgo + "."+ format;

        File fichier = new File(nomFichier);

       if(format.equals("json")) {
           ObjectMapper mapper = new ObjectMapper();
           try{
               mapper.writeValue(fichier, algo);
               System.out.println("success json file");
           } catch (JsonMappingException e) {
               throw new RuntimeException(e);
           } catch (JsonGenerationException e) {
               throw new RuntimeException(e);
           } catch (IOException e) {
               throw new RuntimeException(e);
           }
       }else if (format.equals("xml")) {
           XmlMapper xmlMapper = new XmlMapper();
           try {
               xmlMapper.writeValue(fichier, algo);
               System.out.println("success xml file");
           } catch (IOException e) {
               throw new RuntimeException(e);
           }
       }

    }

    public AlgorithmeI chargeAlgo(String nomAlgoRecherche) {


        return null;
    }

    public static void main(String[] args) {
        Map<String, String> propriete = new HashMap<>();
        propriete.put("format-fichiers-parametres", "xml");
        IO io = new IO(propriete);
//        io.sauvegardeAlgo(new RotationParametre());
//        io.sauvegardeAlgo(new DecalageCle());
//        io.sauvegardeAlgo(new DecalageParametre());
        io.sauvegardeAlgo(new RotationParametre());
    }
}