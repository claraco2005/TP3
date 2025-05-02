package h25.msd.poo2.fx;

import javafx.scene.control.TreeCell;

import java.lang.reflect.Modifier;

public class AlgoClassTreeCell extends TreeCell<AlgoWrapper> {
    public void updateItem(AlgoWrapper algoWrapper, boolean empty) {
        super.updateItem(algoWrapper, empty);
        if (algoWrapper != null && !empty) {

            if (Modifier.isAbstract(algoWrapper.getClasse().getModifiers())) {
                setText(algoWrapper.getClasse().getSimpleName());
                if (!algoWrapper.getClasse().isInterface()) {
                    setStyle("-fx-text-fill: lightgray");
                } else {
                    setStyle("-fx-text-fill: green;-fx-font-style: italic;");

                }
            } else {
                setText(algoWrapper.getAlgo().getNom());
                setStyle("-fx-text-fill: darkblue;-fx-font-weight: bold;");
            }
        } else {
            setItem(null);
//            setGraphic(null);
            setText(null);
        }
    }
}
