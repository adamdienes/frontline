package frontline.panels;

import frontline.persistence.LevelEditorEngine;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import static java.awt.BorderLayout.*;

public class LevelEditorPanel extends JPanel{
    protected LevelEditorEngine LevelEditor;
    private final JPanel toolKit, tableHolder;
    private JTable table;
    private final JButton treeButton, waterButton, castle1Button, castle2Button, barrack1Button, barrack2Button, eraseButton, saveButton;

    public LevelEditorPanel() {
        LevelEditor = new LevelEditorEngine();
        toolKit = new JPanel();
        tableHolder = new JPanel();
        treeButton = new JButton("<html><font color = black>Fa</font></html>");
        waterButton = new JButton("<html><font color = black>Víz</font></html>");
        castle1Button = new JButton("<html><font color = black>1. Kastély</font></html>");
        castle2Button = new JButton("<html><font color = black>2. Kastély</font></html>");
        barrack1Button = new JButton("<html><font color = black>1. Barakk</font></html>");
        barrack2Button = new JButton("<html><font color = black>2. Barakk</font></html>");
        eraseButton = new JButton("<html><font color = black>Törlés</font></html>");
        saveButton = new JButton("<html><font color = black>Pálya mentése</font></html>");
        table = new JTable(10, 20){
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1400, 750));

        //table
        tableHolder.setBounds(0, 0, 1200, 500);
        tableHolder.setOpaque(true);
        tableHolder.setBackground(Color.GRAY);
        table.setFont(new Font("Serif", Font.BOLD, 50));
        table.setRowSelectionAllowed(false);

        table.setRowHeight(70);
        for (int i = 0; i < 20; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(70);
        }

        tableHolder.add(table);
        add(tableHolder);

        //buttons
        treeButton.setPreferredSize(new Dimension(200,80));
        waterButton.setPreferredSize(new Dimension(200,80));
        castle1Button.setPreferredSize(new Dimension(200,80));
        castle2Button.setPreferredSize(new Dimension(200,80));
        barrack1Button.setPreferredSize(new Dimension(200,80));
        barrack2Button.setPreferredSize(new Dimension(200,80));
        saveButton.setPreferredSize(new Dimension(200,80));
        eraseButton.setPreferredSize(new Dimension(200,80));

        treeButton.setBackground(new Color(178,212,178));
        waterButton.setBackground(new Color(119,158,203));
        castle1Button.setBackground(new Color(255,105,97));
        castle2Button.setBackground(new Color(244,238,177));
        barrack1Button.setBackground(new Color(254,216,177));
        barrack2Button.setBackground(new Color(155,103,60));
        saveButton.setBackground(new Color(177,156,217));
        eraseButton.setBackground(new Color(255,255,255));

        treeButton.setOpaque(true);
        waterButton.setOpaque(true);
        castle1Button.setOpaque(true);
        castle2Button.setOpaque(true);
        barrack1Button.setOpaque(true);
        barrack2Button.setOpaque(true);
        saveButton.setOpaque(true);
        eraseButton.setOpaque(true);

        //listeners
        treeButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LevelEditor.setCurrentItem("f");
            }
        });
        waterButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LevelEditor.setCurrentItem("s");
            }
        });
        castle1Button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LevelEditor.setCurrentItem("#");
            }
        });
        castle2Button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LevelEditor.setCurrentItem("@");
            }
        });
        barrack1Button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LevelEditor.setCurrentItem("k");
            }
        });
        barrack2Button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LevelEditor.setCurrentItem("p");
            }
        });
        eraseButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LevelEditor.setCurrentItem("-");
            }
        });

        //saveButton action
        saveButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == saveButton) {
                    if (LevelEditor.validLevel()){
                        JFrame saveFrame = new JFrame();
                        JFileChooser fileChooser = new JFileChooser();
                        fileChooser.setDialogTitle("Mentés helye");
                        File workingDirectory = new File(System.getProperty("user.dir") + "/res/levels/userLevels");
                        fileChooser.setCurrentDirectory(workingDirectory);

                        if (LevelEditor.saveToFile(fileChooser, saveFrame)){
                            JOptionPane.showConfirmDialog(null, "Sikeres pályamentés fájlba!", "Sikeres mentés", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
                        }
                    } else {
                        JOptionPane.showConfirmDialog(null, "Hiba a pályaszerkesztés közben! Játékosonként 1-1 kastély engedélyezett.", "Hiba: kastélyok száma", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
                    }
                }
            }
        });

        //table listener
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());
                if (row >= 0 && col >= 0) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    String c = "<html><font color = " + LevelEditor.getCurrentColor() + ">■</font></html>";
                    table.setValueAt(c, row, col);
                    LevelEditor.setLevelItem(row, col);
                }
            }
        });

        //setup toolkit
        toolKit.setPreferredSize(new Dimension(1400,50));
        toolKit.setOpaque(true);
        toolKit.setBackground(Color.GRAY);
        toolKit.setLayout(new GridLayout(1,3));

        //add toolkit buttons
        toolKit.add(treeButton);
        toolKit.add(waterButton);
        toolKit.add(castle1Button);
        toolKit.add(castle2Button);
        toolKit.add(barrack1Button);
        toolKit.add(barrack2Button);
        toolKit.add(eraseButton);
        toolKit.add(saveButton);

        add(toolKit, SOUTH);
    }
}