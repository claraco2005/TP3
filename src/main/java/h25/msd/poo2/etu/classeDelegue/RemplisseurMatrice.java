/*Mame Diarra Bousso Ndiaye*/

package h25.msd.poo2.etu.classeDelegue;

public class RemplisseurMatrice {

    public char[][] remplirMatrice(String texteLisible, int index, int rangee, int colonne) {
        char[] caracteresTexte = texteLisible.toCharArray();
        char[][] matrice = new char[rangee][colonne];
        for (int i = 0; i < matrice.length; i++) {
            for (int j = 0; j < matrice[i].length; j++) {
                if (index < caracteresTexte.length) {
                    matrice[i][j] = caracteresTexte[index];
                    index++;
                } else {
                    matrice[i][j] = '#';
                }
            }

        }

        return matrice;
    }
}
