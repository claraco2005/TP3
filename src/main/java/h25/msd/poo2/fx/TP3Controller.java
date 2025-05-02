package h25.msd.poo2.fx;

import h25.msd.poo2.echange.*;
import h25.msd.poo2.etu.Model;
import h25.msd.poo2.etu.exception.TP3Exception;
import h25.msd.poo2.etu.utilisateur.AbstractUtilisateur;
import h25.msd.poo2.etu.utilisateur.Administrateur;
import h25.msd.poo2.etu.utilisateur.Invite;
import h25.msd.poo2.etu.utilisateur.Utilisateur;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.File;
import java.util.*;

public class TP3Controller implements ApplicationUI {

    private final AlgoFinder algoFinder = new AlgoFinder();
    private String premierChoixTriAlgo = "";
    private Stage stage;//pour afficher le nom de l'utilisateur
    private ApplicationModelI applicationModel;
    private MoteurRechercheI moteurRecherche;
    private GestionnaireFichierI gestionnaireFichiers;

    @FXML
    private TreeView<AlgoWrapper> algoTreeView;

    @FXML
    private TableView<Map.Entry<String, String>> parametresTableView;

    @FXML
    private TableColumn<Map.Entry<String, String>, String> nomTableColumn;

    @FXML
    private TableColumn<Map.Entry<String, String>, String> valeurTableColumn;

    @FXML
    private TextArea texteLisibleArea;

    @FXML
    private ChoiceBox<AlgorithmeI> choixAlgoChoiceBox;

    @FXML
    private TextArea textEncrypteArea;

    @FXML
    private TextArea resultatEncrypteArea;

    @FXML
    private TextArea resultatOriginalTextArea;

    @FXML
    private TextArea texteADecrypte;

    @FXML
    private ListView<ResultatI> historiqueListView;


    @FXML
    private ListView<ResultatI> resultatRechercheListView;

    @FXML
    private TextField motRechercheTextField;

    @FXML
    private TextField nomAlgoTextField;

    @FXML
    private TextField nombreUsagesTextField;

    @FXML
    private TextField parametreValeurTextField;

    @FXML
    private ChoiceBox<String> ordreTriChoiceBox;

    @FXML
    private ComboBox<AbstractUtilisateur> userComboBox;

    @FXML
    void decrypte(ActionEvent event) {
        crypte((AlgorithmeI algo, String texte) -> algo.decrypte(texte), textEncrypteArea, texteLisibleArea);
    }

    @FXML
    void encrypte(ActionEvent event) {
        crypte((AlgorithmeI algo, String texte) -> algo.encrypte(texte), texteLisibleArea, textEncrypteArea);
    }

    private void crypte(Crypte crypte, TextArea sourceArea, TextArea cibleArea) {
        AlgorithmeI algo = choixAlgoChoiceBox.getSelectionModel().getSelectedItem();
        String texte = sourceArea.getText();
        String texteEncrypte = "Échec de l'algorithme!";
        if (algo != null && texte != null) {
            texteEncrypte = crypte.crypte(algo, texte);
            cibleArea.setText(texteEncrypte);
        }
        algoTreeView.refresh();
    }


    @Override
    public void ajouteResultat(ResultatI resultat) {
        assert resultat != null : "null resultat";
        historiqueListView.getItems().add(resultat);
        if (moteurRecherche != null) {
            moteurRecherche.ajouteResultat(resultat);

        }
    }

    @FXML
    void changeParametreValeur(ActionEvent event) {
        AlgorithmeI algo = algoTreeView.getSelectionModel().getSelectedItem().getValue().getAlgo();
        Map.Entry<String, String> selectedEntry = parametresTableView.getSelectionModel().getSelectedItem();
        String nouvelleValeur = parametreValeurTextField.getText();

        if (algo != null && selectedEntry != null && algo instanceof AlgorithmeAvecParametreI) {
            AlgorithmeAvecParametreI algoParam = (AlgorithmeAvecParametreI) algo;
            String message = algoParam.valideParametre(selectedEntry.getKey(), nouvelleValeur);
            if (message == "") {
                algoParam.setParametre(selectedEntry.getKey(), nouvelleValeur);
                selectedEntry.setValue(nouvelleValeur);
                parametresTableView.refresh();
            } else {
                informeUtilisateur(message);
            }
        }

    }


    @FXML
    public void initialize() {
        System.out.println("Initialisation de l'UI");

        applicationModel = new Model();
        applicationModel.initialise(this);
        if (gestionnaireFichiers != null) {
            gestionnaireFichiers.prepareDossiersRequis();

        }

        prepareCreateurAlgo();
        createAlgosMenu();
        resultatRechercheListView.getSelectionModel().selectedItemProperty().addListener((o, v, n) -> {
            historiqueListView.getSelectionModel().select(n);
        });

        intialiseMenuTri();
        initialiseSelectionResultats();
        initUtilisateurs();
    }

    private void initUtilisateurs() {
        userComboBox.getItems().addAll(
                new Administrateur("Admin"),
                new Invite(),
                new Utilisateur(applicationModel.getNomsAuteurs())
        );

        userComboBox.setConverter(new StringConverter<AbstractUtilisateur>() {
            @Override
            public String toString(AbstractUtilisateur object) {
                String retNom = null;
                if (object != null) {
                    retNom = object.getNom();
                }

                return retNom;
            }

            @Override
            public Utilisateur fromString(String string) {
                return new Utilisateur(string);
            }
        });
        userComboBox.setOnAction(event -> {
            AbstractUtilisateur utilisateurName = userComboBox.getSelectionModel().getSelectedItem();
            if (!userComboBox.getItems().contains(utilisateurName)) {
                userComboBox.getItems().add(utilisateurName);
            }

        });
    }

    private void initialiseSelectionResultats() {
        historiqueListView.getSelectionModel().selectedItemProperty().addListener((o, v, n) -> {
            resultatOriginalTextArea.setText(n.getTexteOriginal());
            resultatEncrypteArea.setText(n.getTexteFinal());
        });
        resultatRechercheListView.getSelectionModel().selectedItemProperty().addListener((o, v, n) -> {
            resultatOriginalTextArea.setText(n.getTexteOriginal());
            resultatEncrypteArea.setText(n.getTexteFinal());
        });
    }

    private void intialiseMenuTri() {
        ordreTriChoiceBox.getItems().addAll(List.of("Algo puis taille du texte original", "Algo puis date", "Nombre d'utilisation de l'algo puis date"));
    }

    private void prepareCreateurAlgo() {
        //retrouver les algos
        algoTreeView.setRoot(algoFinder.buildAllProduitTreeItem());
        algoTreeView.setCellFactory(p -> new AlgoClassTreeCell());
        algoTreeView.setShowRoot(true);

    }


    public void createAlgosMenu() {
        List<AlgorithmeI> algos = retourveAlgosConcrets();

        //ajouter dans la boite de choix d'algo
        choixAlgoChoiceBox.getItems().addAll(algos);


        nomTableColumn.setCellValueFactory((cellData) -> {
            return new SimpleStringProperty(cellData.getValue().getKey());
        });
        valeurTableColumn.setCellValueFactory((cellData) -> {
            return new SimpleStringProperty(cellData.getValue().getValue());
        });

        algoTreeView.getSelectionModel().selectedItemProperty().addListener(
                (o, v, n) -> {
                    AlgorithmeI algo = o.getValue().getValue().getAlgo();
                    if (algo != null) {

                        nomAlgoTextField.setText(algo.getNom());
                        nombreUsagesTextField.setText(String.valueOf(algo.getNombreUsages()));

                        if (algo instanceof AlgorithmeAvecParametreI) {
                            AlgorithmeAvecParametreI algoParam = (AlgorithmeAvecParametreI) algo;
                            updateParametres(algoParam);
                        }

                    } else {
                        nomAlgoTextField.setText(o.getValue().getValue().getClasse().getSimpleName());
                        nombreUsagesTextField.setText("aucun");
                        parametresTableView.getItems().clear();
                        parametresTableView.refresh();
                    }
                });

    }

    private void updateParametres(AlgorithmeAvecParametreI algoParam) {
        Map<String, String> parametresMap = new HashMap<>(algoParam.getParametres());// pour supporter les Map.of

        parametresTableView.getItems().clear();
        parametresTableView.getItems().addAll(parametresMap.entrySet());
        parametresTableView.refresh();
    }

    private List<AlgorithmeI> retourveAlgosConcrets() {
        List<AlgorithmeI> algos = new ArrayList<>();

        algos = new ArrayList<>(algoFinder.getAlgosConcrets());
        for (AlgorithmeI algo : algos) {
            algo.setApplicationUI(this);
        }
        return algos;
    }

    private void remplitMenuRecherche(Object[] items, ChoiceBox<String> choiceBox) {
        choiceBox.getItems().clear();
        for (Object item : items) {
            choiceBox.getItems().add(item.toString());
        }
    }

    private void changeVisibility(boolean visible, Control... controls) {
        for (Control control : controls) {
            control.setVisible(visible);
        }
    }

    @FXML
    void petitGrand(ActionEvent event) {
        ToggleButton petitGrand = (ToggleButton) event.getSource();
        if (petitGrand.isSelected()) {
            petitGrand.setText("<");
        } else {
            petitGrand.setText(">");
        }
    }

    @FXML
    void rechercheResultat(ActionEvent event) {
        Set<ResultatI> resultatsTrouve = moteurRecherche.recherche(motRechercheTextField.getText());
        if (resultatsTrouve != null) {
            resultatRechercheListView.getItems().clear();
            resultatRechercheListView.getItems().addAll(resultatsTrouve);
        }
    }


    @FXML
    void triResultats(ActionEvent event) {
        String ordre = ordreTriChoiceBox.getSelectionModel().getSelectedItem();
        if (ordre != null) {
            CritereTri critereTri = switch (ordre) {
                case "Algo puis taille du texte original" -> CritereTri.ALGO_PUIS_TAILLE_TEXTE_ORIGINAL;
                case "Algo puis date" -> CritereTri.ALGO_PUIS_DATE;
                case "Nombre d'utilisation de l'algo puis date" -> CritereTri.NOMBRE_UTILISATION_PUIS_DATE;
                default -> null;
            };
            if (critereTri != null) {
                moteurRecherche.triResultat(critereTri, resultatRechercheListView.getItems());
                moteurRecherche.triResultat(critereTri, historiqueListView.getItems());
            }
        }
    }

    @FXML
    void changeUtilisateur(ActionEvent event) {
        System.out.println("allo utili");
    }

    public void setStage(Stage primaryStage) {
        this.stage = primaryStage;
        stage.setTitle("TP3 par " + applicationModel.getNomsAuteurs());
    }

    // TP3 - FICHIERs  ---------------------------

    @FXML
    void RecreerDossiersDeparts(ActionEvent event) {
        //applicationModel.
    }

    @FXML
    void effacerTousLesDossiers(ActionEvent event) {
        gestionnaireFichiers.viderDossiersFichiers();
    }


    @FXML
    void chargeOriginal(ActionEvent event) {
        File dossierDefaut = gestionnaireFichiers.getDossier(Dossiers.TEXTES_ORIGINAUX);
        chargerFichierTexte(dossierDefaut, " Chargement du fichier original", texteLisibleArea);
    }

    @FXML
    void sauvegardeOriginal(ActionEvent event) {
        File dossierDefaut = gestionnaireFichiers.getDossier(Dossiers.TEXTES_ORIGINAUX);
        sauvegarderFichier(dossierDefaut, " Sauvegarde du fichier original", texteLisibleArea);
    }

    @FXML
    void chargeEncrypte(ActionEvent event) {
        File dossierDefaut = gestionnaireFichiers.getDossier(Dossiers.ENCRYPTIONS);
        chargerFichierTexte(dossierDefaut, " Chargement du fichier encrypte", textEncrypteArea);
    }

    @FXML
    void sauvegardeEncrypte(ActionEvent event) {
        File dossierDefaut = gestionnaireFichiers.getDossier(Dossiers.ENCRYPTIONS);
        sauvegarderFichier(dossierDefaut, " Sauvegarde du fichier encrypte", textEncrypteArea);
    }

    @FXML
    void chargerParametreSelectionne(ActionEvent event) {
        AlgorithmeI algo = algoTreeView.getSelectionModel().getSelectedItem().getValue().getAlgo();
        try {
            gestionnaireFichiers.chargeParametresDansAlgo(algo);
            algoTreeView.refresh();
            if (algo instanceof AlgorithmeAvecParametreI) {
                updateParametres((AlgorithmeAvecParametreI) algo);
            }
        } catch (TP3Exception e) {
            informeUtilisateur(e.getMessage());
        }
    }

    @FXML
    void sauvegarderParametreSelectionne(ActionEvent event) {
        AlgorithmeI algo = algoTreeView.getSelectionModel().getSelectedItem().getValue().getAlgo();
        try {
            gestionnaireFichiers.sauvegarderParametreSelectionne(algo);
        } catch (TP3Exception e) {
            informeUtilisateur(e.getMessage());
        }
    }

    @FXML
    void enregistreUtilisateur(ActionEvent event) {
        try {
            AbstractUtilisateur abstractUtilisateur = userComboBox.getSelectionModel().getSelectedItem();
            if (abstractUtilisateur != null) {
                gestionnaireFichiers.sauvegardeUtilisateur(abstractUtilisateur);
            }
        } catch (TP3Exception e) {
            informeUtilisateur(e.getMessage());
        }
    }

    @FXML
    void chargeUtilisateur(ActionEvent event) {
        try {
            AbstractUtilisateur abstractUtilisateur = gestionnaireFichiers.chargeUtilisateur();
            if (abstractUtilisateur != null) {
                if (!userComboBox.getItems().contains(abstractUtilisateur)) {
                    userComboBox.getItems().add(abstractUtilisateur);
                }
                userComboBox.getSelectionModel().select(abstractUtilisateur);
            }
        } catch (TP3Exception e) {
            informeUtilisateur(e.getMessage());
        }
    }


    //-- AUtres méthodes --------------------------

    public void chargerFichierTexte(File dossierDepart, String message, TextArea AireTexteImpliquee) {
        try {
            String texte = gestionnaireFichiers.chargeTexte(
                    demandeFichier(
                            "txt", message, false, dossierDepart)
            );
            if (texte != null) {
                AireTexteImpliquee.setText(texte);
            }
        } catch (TP3Exception tp3e) {
            informeUtilisateur(tp3e.getMessage());
        }
    }

    public void sauvegarderFichier(File dossierDepart, String message, TextArea AireTexteImpliquee) {
        try {
            if (AireTexteImpliquee.getText() != null) {
                gestionnaireFichiers.enregistreTexte(
                        demandeFichier(
                                "txt", message, true, dossierDepart),
                        AireTexteImpliquee.getText()
                );
            }

        } catch (TP3Exception tp3e) {
            informeUtilisateur(tp3e.getMessage());
        }
    }

    private File demandeFichier(String type, String message, boolean isSaving, File dossierDefaut) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(dossierDefaut);
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*." + type));

        return isSaving ?
                fileChooser.showSaveDialog(null) :
                fileChooser.showOpenDialog(null);
    }

    public void setMoteurRecherche(MoteurRechercheI moteurRecherche) {
        this.moteurRecherche = moteurRecherche;
    }

    @Override
    public void setGestionnaireFichiers(GestionnaireFichierI gestionnaire) {
        gestionnaireFichiers = gestionnaire;
    }


    @Override
    public void informeUtilisateur(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("TP2 Cryptage");
        alert.setHeaderText("Avertisement!");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public AbstractUtilisateur getUtilisateur() {
        return userComboBox.getSelectionModel().getSelectedItem();
    }

    interface Crypte {
        String crypte(AlgorithmeI algo, String texte);
    }
}
