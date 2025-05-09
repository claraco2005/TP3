package h25.msd.poo2.etu.algo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import h25.msd.poo2.echange.AlgorithmeI;

import java.util.*;

public abstract class AbstractAlgoCryptographique implements AlgorithmeI {

    private Map<Character, Integer> charMap = new TreeMap<>();
    private Map<Integer, Character> intMap = new TreeMap<>();
    private List<Character> alphabetMinuscule = Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z');
    private List<Character> alphabetMajuscule = Arrays.asList('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z');
    private List<Character> caractereSpeciaux = Arrays.asList(':', '‘', ',', '.', ';', '!', '?', '"', '-', ' ', '#');
    private List<Character> nombres = Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
    @JsonIgnore
    private Character CHARACTERE_NON_SUPPORTE = '#';

    @JsonIgnore
    public Map<Character, Integer> getCharMap() {
        return charMap;
    }


    /**
     * Qui crée un set trié (enfant de SortedSet) contenant les lettres qu’on désire utiliser
     *
     * @return un sortedSet
     */

    public SortedSet<Character> produitCaractereSet() {
        SortedSet<Character> characterSortedSet = new TreeSet<>();
        characterSortedSet.addAll(alphabetMinuscule);
        characterSortedSet.addAll(alphabetMajuscule);
        characterSortedSet.addAll(caractereSpeciaux);
        characterSortedSet.addAll(nombres);
        construitLettreChiffreTransposition(characterSortedSet);
        return characterSortedSet;
    }

    /**
     * qui attribue un nombre à tous les caractères reçus dans un ensemble de lettres.
     *
     * @param tousCaracteres le SortedSet des caracteres
     */

    public void construitLettreChiffreTransposition(SortedSet<Character> tousCaracteres) {
        int i = 0;
        for (Character character : tousCaracteres) {
            charMap.put(character, i);
            intMap.put(i, character);
            i++;
        }
    }

    /**
     * transforme  une chaine de caractere en liste de nombre  selon les
     * transpositions établies.
     *
     * @param texteOriginal la chaine a transformer
     * @return une liste de nombre
     */

    public List<Integer> traduitTexteEnListe(String texteOriginal) {  // transforme  une chaine d ecaractere en liste de nombre
        produitCaractereSet();
        char[] tabLettre = texteOriginal.toCharArray();
        List<Integer> listeNombre = new ArrayList<>();
        for (int i = 0; i < tabLettre.length; i++) {
            if (charMap.containsKey(tabLettre[i])) {
                listeNombre.add(charMap.get(tabLettre[i]));
            } else {
                listeNombre.add(charMap.get(CHARACTERE_NON_SUPPORTE));
            }
        }
        return listeNombre;
    }

    /**
     * transforme une liste de nombre en chaine de caractere
     *
     * @param caractereList la liste a transformer
     * @return une chaine de caractere
     */


    public String traduitListeEnTexte(List<Integer> caractereList) {
        String mot = "";
        for (int i = 0; i < caractereList.size(); i++) {
            if (intMap.containsKey(caractereList.get(i))) {
                mot += intMap.get(caractereList.get(i));
            }
        }
        return mot;
    }
    @JsonIgnore
    public Character getCHARACTERE_NON_SUPPORTE() {
        return CHARACTERE_NON_SUPPORTE;
    }
}
