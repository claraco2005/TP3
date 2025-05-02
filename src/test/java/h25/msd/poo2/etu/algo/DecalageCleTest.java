package h25.msd.poo2.etu.algo;

import h25.msd.poo2.echange.AlgorithmeI;
import h25.msd.poo2.echange.BidonUI;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DecalageCleTest {
    @Test
    void testEncrypteDecalageFixeSimple() {
        AlgorithmeI algo = new DecalageFixe(); //décalage de 5
        algo.setApplicationUI(new BidonUI());
        assertEquals("fqqt",algo.encrypte("allo"));
    }

    @Test
    void testEncrypteDecalageFixeComplet() {
        String text = "abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLNOPQRSTUVWXYZ,.;:!?'\"";
        AlgorithmeI algo = new DecalageFixe(); //décalage de 5
        algo.setApplicationUI(new BidonUI());
//        ',', '.', ';', ':', '!', '?', ' ', '\'', '"'
        assertEquals("fghijklmnopqrstuvwxyz !\"#'6789:;?AB5FGHIJKLMNOPQSTUVWXYZabcde34DC.E20",algo.encrypte(text));
    }

    @Test
    void testEncrypteDecryptDecalageInconnus() {
        String text = "a$b&c*#";
        AlgorithmeI algo = new DecalageFixe(); //décalage de 5
        algo.setApplicationUI(new BidonUI());
//        ',', '.', ';', ':', '!', '?', ' ', '\'', '"'
        assertEquals("a#b#c##",algo.decrypte(algo.encrypte(text)));    }
}