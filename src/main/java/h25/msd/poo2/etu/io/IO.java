/*Mame Diarra Bousso Ndiaye */
package h25.msd.poo2.etu.io;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import h25.msd.poo2.echange.AlgorithmeAvecParametreI;
import h25.msd.poo2.echange.AlgorithmeI;
import h25.msd.poo2.echange.ApplicationUI;

import h25.msd.poo2.echange.BidonUI;
import h25.msd.poo2.etu.algo.RotationParametre;
import h25.msd.poo2.etu.exception.TP3Exception;


import java.io.File;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;


public class IO {
    private Map<String, String> propriete;
    ApplicationUI ui;
    String BASE_PATH = "parametres/";


    public IO(ApplicationUI ui, Map<String, String> propriete) {
        this.ui = ui;
        this.propriete = propriete;
    }

    /**
     * Sauvegarde un algo sur le disque dans le format spécifié(Json ou xml)
     * Le fichier est enregistré dans le dossier parametres avec comme nom le de l'algo
     *
     * @param algo l'algo à sauvegarder
     * @throws TP3Exception gestion erreur
     */
    public void sauvegardeAlgo(AlgorithmeI algo) throws TP3Exception {
        String nomAlgo = algo.getNom();
        String format = propriete.get("format-fichiers-parametres");
        String nomFichier = nomAlgo + "." + format;


        File fichier = new File(BASE_PATH + nomFichier);

        if (algo instanceof AlgorithmeAvecParametreI) {

            if (!fichier.exists()) {

                if (format.equals("json")) {
                    ObjectMapper mapper = new ObjectMapper();

                    try {
                        mapper.writeValue(fichier, algo);

                    } catch (IOException e) {
                        throw new TP3Exception("erreur le fichier json n'a pas été créer", ui.getUtilisateur());
                    }

                } else if (format.equals("xml")) {
                    XmlMapper xmlMapper = new XmlMapper();

                    try {
                        xmlMapper.writeValue(fichier, algo);

                    } catch (NullPointerException e) {
                        throw new TP3Exception("Le fichier n'existe pas", ui.getUtilisateur());
                    } catch (IOException e) {
                        throw new TP3Exception("erreur le fichier xml n'a pas été créer", ui.getUtilisateur());
                    }
                }
            }
        } else {
            throw new TP3Exception("Cet algo n'a pas de paramètre à sauvegarder", ui.getUtilisateur());
        }


    }

    /**
     * Charge un algo sur le disque à partir de son nom et du format spécifier
     * le fichier est lu depuis le dossier parametres
     *
     * @param nomAlgoRecherche nom de l'algo
     * @return algo
     * @throws TP3Exception
     */
    public AlgorithmeI chargeAlgo(String nomAlgoRecherche) throws TP3Exception {
        String format = propriete.get("format-fichiers-parametres");

        File fichier = new File(BASE_PATH + nomAlgoRecherche + "." + format);
        ObjectMapper mapper = null;

        if (format.equals("json")) {

            mapper = new ObjectMapper();

        } else if (format.equals("xml")) {

            mapper = new XmlMapper();

        }
        try {

            assert mapper != null;
            return mapper.readValue(fichier, AlgorithmeI.class);

        } catch (IOException e) {
            throw new TP3Exception("erreur le fichier  n'a pu etre chargé, il est peut etre vide ou inexistant", ui.getUtilisateur());
        }

    }

    public static void main(String[] args) throws TP3Exception {
        Map<String, String> propriete = new HashMap<>();
        ApplicationUI ui = new BidonUI();
        propriete.put("format-fichiers-parametres", "json");
        IO io = new IO(ui, propriete);
        io.sauvegardeAlgo(new RotationParametre());
//        io.sauvegardeAlgo(new DecalageCle());
//        io.sauvegardeAlgo(new DecalageParametre());
//        io.sauvegardeAlgo(new DecalageFixe());

    }
}