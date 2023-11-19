package frontline.persistence;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;

public class LevelEditorEngine {
    private String currentItem;
    private String[][] level;
    private int castleA, castleB;

    public LevelEditorEngine() {
        currentItem = null;
        level = new String[10][20];
        initLevel();
    }

    //fill level with empty items (-)
    public void initLevel(){
        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 20; j++){
                level[i][j] = "-";
            }
        }
    }

    //only one castle per player is allowed
    public boolean validLevel(){
        castleA = 0;
        castleB = 0;

        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 20; j++) {
                if (level[i][j] == "#"){ castleA++; }
                if (level[i][j] == "@"){ castleB++; }
            }
        }
        return (castleA == 1 && castleB == 1);
    }

    public boolean saveToFile(JFileChooser fileChooser, JFrame saveFrame) {
        int response = fileChooser.showSaveDialog(saveFrame);
        if (response == JFileChooser.APPROVE_OPTION) {
            try {
                FileWriter fr = new FileWriter(fileChooser.getSelectedFile() + ".txt");

                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 20; j++) {
                        fr.write(getLevelItem(i, j));
                    }
                    fr.write("\n");
                }
                fr.close();
            } catch (IOException ex) {
                System.out.println(ex);
                ex.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public String getCurrentColor() {
        if (currentItem == "f") return "green";
        if (currentItem == "s") return "blue";
        if (currentItem == "#") return "red";
        if (currentItem == "@") return "yellow";
        if (currentItem == "k") return "orange";
        if (currentItem == "p") return "brown";
        return "white";
    }

    public String getLevelItem(int i, int j) {
        return level[i][j];
    }

    public void setCurrentItem(String currentItem) {
        this.currentItem = currentItem;
    }

    public void setLevelItem(int i, int j) {
        level[i][j] = currentItem;
    }
}