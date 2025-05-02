package h25.msd.poo2.fx;

import h25.msd.poo2.echange.AlgorithmeAvecParametreI;
import h25.msd.poo2.echange.AlgorithmeI;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Martin Simoneau
 */
public class AlgoFinder {

    private static final String DEFAULT_VALUE = "1";
    private static int lastNumber = 0;

    private static int nomCompteur = 0;

    private Map<Class, Set<Class>> classMap = new HashMap<>();
    private Map<Class, Set<Class>> interfaceMap = new HashMap<>();
    private Map<Class, Set<Class>> implementationsMap = new HashMap<>();
    private Map<Class, AlgorithmeI> algosDejaInstancieMap = new HashMap<>();

    public AlgoFinder() {
    }

    private Class getPrimitive(Class baseType) {
        return Array.get(Array.newInstance(baseType, 1), 0).getClass();
    }

    public AlgorithmeI instancieAlgo(Class classeRequise) {
        AlgorithmeI retProduit;
        Constructor constructor = null;
        constructor = classeRequise.getDeclaredConstructors()[0];// on tient pour acquis un seul constructeur
        Parameter[] parameters = constructor.getParameters();

        try {
            if (parameters.length > 0) {
                Object[] parametersValues = convertStringsToValues(
                        parameters,
                        demandeValeursUtilisateur(parameters, classeRequise.getSimpleName()));
                retProduit = (AlgorithmeI) constructor.newInstance(parametersValues);
            } else {
                retProduit = (AlgorithmeI) constructor.newInstance();
            }

        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        return retProduit;
    }

    private Object[] convertStringsToValues(Parameter[] parameters, String[] valeursTexte) {
        Object[] retValues = new Object[parameters.length];
        try {
            for (int i = 0; i < parameters.length; i++) {
                Class classeActuelle = parameters[i].getType();
                if (classeActuelle != String.class) {//conversion en emballeur
                    Method method = getPrimitive(classeActuelle).getDeclaredMethod("valueOf", String.class);
                    retValues[i] = method.invoke(null, valeursTexte[i]);
                } else {
                    retValues[i] = valeursTexte[i];
                }
            }
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return retValues;
    }

    public TreeItem<AlgoWrapper> buildAllProduitTreeItem() {

        Set<Class> allProduitClasses = findAllClassesUsingClassLoader("h25.msd.poo2.etu.algo");
        buildAllHierarchiesMap(allProduitClasses);

        //ajouteClasseTreeNodeRec(AlgorithmeAvecParametreI.class, root);
        TreeItem<AlgoWrapper> root = ajouteClasseTreeNodeRec(AlgorithmeI.class);

        return root;
    }

    private TreeItem<AlgoWrapper> ajouteClasseTreeNodeRec(Class actualClass
    ) {
        TreeItem<AlgoWrapper> nouveauNode = new TreeItem<>(new AlgoWrapper(actualClass));
        if (!isAbstract(actualClass)) {
            nouveauNode.getValue().setAlgo(instancieAlgoUneSeuleFois(actualClass));
        }
        Set<Class> actualClasseSet = (actualClass.isInterface() ? interfaceMap : classMap).get(actualClass);
        if (actualClasseSet != null) { //gère les parents
            for (Class classe : actualClasseSet) {
                TreeItem<AlgoWrapper> subNode = ajouteClasseTreeNodeRec(classe);
                nouveauNode.getChildren().add(subNode);
            }
        }
        Set<Class> implementations = implementationsMap.get(actualClass);
        if (implementations != null) {
            for (Class implementation : implementations) {
                TreeItem<AlgoWrapper> subNode = ajouteClasseTreeNodeRec(implementation);
                nouveauNode.getChildren().add(subNode);
            }
        }

        return nouveauNode;
    }

    private AlgorithmeI instancieAlgoUneSeuleFois(Class actualClass) {
        if (!algosDejaInstancieMap.containsKey(actualClass)) {
            algosDejaInstancieMap.put(actualClass, instancieAlgo(actualClass));
        }
        return algosDejaInstancieMap.get(actualClass);
    }

    private void buildAllHierarchiesMap(Set<Class> allProduitClasses) {
        for (Class classe : allProduitClasses) {
            if (classe != null) {
                if (classe.isInterface()) {  //gestion des interfaces
                    buildInterfacesMap(classe);
                } else {  // gestion des classes
                    buildClassMap(classe);
                }
            }
        }
        // on ajoute les éléments du packge echange
        interfaceMap.putIfAbsent(AlgorithmeI.class,new HashSet<>());
        interfaceMap.putIfAbsent(AlgorithmeAvecParametreI.class,new HashSet<>());
        interfaceMap.get(AlgorithmeI.class).add(AlgorithmeAvecParametreI.class);
    }

    private void buildInterfacesMap(Class classe) {
        interfaceMap.putIfAbsent(classe, new HashSet<>());
        Class<?>[] interfacesParent = classe.getInterfaces();
        for (Class<?> interfaceParentActuelle : interfacesParent) {
            buildInterfacesMap(interfaceParentActuelle);
            interfaceMap.get(interfaceParentActuelle).add(classe);
        }
    }

    private void buildClassMap(Class classe) {
        classMap.putIfAbsent(classe, new HashSet<Class>());
        Class superclass = classe.getSuperclass();
        if (superclass != null && !superclass.equals(Object.class)) {
            buildClassMap(superclass);
            classMap.get(superclass).add(classe);
        }
        // implémentation d'interfaces
        buildImplementationMap(classe);
    }

    private void buildImplementationMap(Class classe) {
        Class<?>[] interfaces = classe.getInterfaces();
        for (Class<?> interfaceCible : interfaces) {
            implementationsMap.putIfAbsent(interfaceCible, new HashSet<>());
            implementationsMap.get(interfaceCible).add(classe);
        }
    }


    private Set<Class> findAllClassesUsingClassLoader(String packageName) {
        Set<Class> allClasses = new HashSet<>();
        InputStream stream = ClassLoader.getSystemClassLoader()
                .getResourceAsStream(packageName.replaceAll("[.]", "/"));
        if (stream != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            allClasses = reader.lines()
                    .filter(line -> line.endsWith(".class"))
                    .map(line -> getClass(line, packageName))
                    .collect(Collectors.toSet());
        }
        return allClasses;
    }


    private Class getClass(String className, String packageName) {
        try {
            return Class.forName(packageName + "."
                    + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            // handle the exception
        }
        return null;
    }

    private boolean isAbstract(Class classe) {
        return Modifier.isAbstract(classe.getModifiers());
    }


    String[] demandeValeursUtilisateur(Parameter[] caracteristiques, String nom) {
        String[] retVal = new String[caracteristiques.length];
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        VBox root = new VBox();
        createExpendablePane(caracteristiques, root, nom);

        alert.getDialogPane().setExpandableContent(root);
        alert.setContentText(nom + " - saisir les valeurs requises :");


        Optional<ButtonType> reponse = alert.showAndWait();
        if (reponse.isPresent()) {
            if (reponse.get().equals(ButtonType.OK)) {
                int i = 0;
                for (Node node : root.getChildren()) {
                    HBox currentHBox = (HBox) node;
                    String val = ((TextField) currentHBox.getChildren().get(1)).getText();
                    retVal[i++] = val;
                }
            }
        }

        return retVal;
    }

    static void createExpendablePane(Parameter[] caracteristiques, VBox root, String nom) {
        for (Parameter caracteristique : caracteristiques) {
            HBox hbox = new HBox();
            Label label = new Label(caracteristique.getName().replaceAll(
                    String.format("%s|%s|%s",
                            "(?<=[A-Z])(?=[A-Z][a-z])",
                            "(?<=[^A-Z])(?=[A-Z])",
                            "(?<=[A-Za-z])(?=[^A-Za-z])"
                    ),
                    " "
            ) + "  ");
            Label typeLabel = new Label("  (" + caracteristique.getType().getName() + ")");
            TextField textField = new TextField();
            if (caracteristique.getName().equals("nom")) {
                textField.setText(nom + nomCompteur++);

            } else {
                textField.setText(DEFAULT_VALUE);

            }
            hbox.getChildren().addAll(label, textField, typeLabel);
            hbox.setAlignment(Pos.BASELINE_CENTER);
            hbox.setPadding(new Insets(20));
            root.getChildren().add(hbox);
        }
    }

    public Collection<AlgorithmeI> getAlgosConcrets() {
        return algosDejaInstancieMap.values();
    }
}