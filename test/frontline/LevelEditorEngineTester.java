package frontline;

import frontline.persistence.LevelEditorEngine;
import org.junit.Test;
import static org.junit.Assert.*;

public class LevelEditorEngineTester {
    @Test
    public void currentColorTest(){
        LevelEditorEngine le = new LevelEditorEngine();
        le.setCurrentItem("-");
        assertEquals("Empty color", "white", le.getCurrentColor());
        le.setCurrentItem("f");
        assertEquals("Tree color", "green", le.getCurrentColor());
        le.setCurrentItem("s");
        assertEquals("Swamp color", "blue", le.getCurrentColor());
        le.setCurrentItem("#");
        assertEquals("Castle1 color", "red", le.getCurrentColor());
        le.setCurrentItem("@");
        assertEquals("Castle2 color", "yellow", le.getCurrentColor());
        le.setCurrentItem("k");
        assertEquals("Barrack1 color", "orange", le.getCurrentColor());
        le.setCurrentItem("p");
        assertEquals("Barrack2 color", "brown", le.getCurrentColor());
    }

    @Test
    public void initLevelTest(){
        LevelEditorEngine le = new LevelEditorEngine();
        le.initLevel();
        assertEquals("Empty level", "-", le.getLevelItem(0, 0));
        assertEquals("Empty level", "-", le.getLevelItem(2, 3));
        assertEquals("Empty level", "-", le.getLevelItem(5, 7));
        assertEquals("Empty level", "-", le.getLevelItem(9, 13));
        assertEquals("Empty level", "-", le.getLevelItem(9, 19));
        assertEquals("Empty level", "-", le.getLevelItem(6, 7));
    }

    @Test
    public void setLevelItemTest(){
        LevelEditorEngine le = new LevelEditorEngine();
        le.setCurrentItem("f");
        le.setLevelItem(1, 1);
        assertEquals("Tree at 1,1", "f", le.getLevelItem(1, 1));
        le.setCurrentItem("s");
        le.setLevelItem(1, 1);
        assertEquals("Swamp at 1,1", "s", le.getLevelItem(1, 1));
        le.setCurrentItem("@");
        le.setLevelItem(1, 1);
        assertEquals("Castle at 1,1", "@", le.getLevelItem(1, 1));
        le.setCurrentItem("p");
        le.setLevelItem(2, 3);
        assertEquals("Barrack at 2,3", "p", le.getLevelItem(2, 3));
        le.setCurrentItem("k");
        le.setLevelItem(3, 3);
        assertEquals("Barrack at 3,3", "k", le.getLevelItem(3, 3));
    }

    @Test
    public void validLevelTest(){
        LevelEditorEngine le = new LevelEditorEngine();
        le.initLevel();
        le.setCurrentItem("@");
        le.setLevelItem(1, 1);
        le.setLevelItem(2, 2);
        assertFalse("Two A type castle in level", le.validLevel());

        le.initLevel();
        le.setCurrentItem("#");
        le.setLevelItem(3, 2);
        le.setLevelItem(7, 5);
        assertFalse("Two B type castle in level", le.validLevel());

        le.initLevel();
        le.setCurrentItem("#");
        le.setLevelItem(3, 2);
        assertFalse("Only one castle in level", le.validLevel());

        le.initLevel();
        le.setCurrentItem("#");
        le.setLevelItem(3, 2);
        le.setCurrentItem("@");
        le.setLevelItem(1, 1);
        le.setLevelItem(2, 4);
        assertFalse("One A type castle and two B type castle in level", le.validLevel());

        le.initLevel();
        le.setCurrentItem("#");
        le.setLevelItem(3, 2);
        le.setCurrentItem("@");
        le.setLevelItem(1, 1);
        assertTrue("Two castles in level", le.validLevel());
    }
}