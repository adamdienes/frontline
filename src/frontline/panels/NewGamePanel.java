package frontline.panels;

import frontline.MainWindow;
import frontline.res.ResourceLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class NewGamePanel extends JPanel {
    private final Image background = ResourceLoader.loadImage("graphics/menu_background.png");
    MainWindow window;
    private File userFile = null;

    public NewGamePanel(MainWindow window) {
        setBackground(Color.white);
        this.window = window;
        setPreferredSize(new Dimension(1400, 750));

        //static level
        String[] choices = {"Pálya 1", "Pálya 2", "Pálya 3", "Pálya 4", "Pálya 5", "Pálya 6"};
        final JLabel l = new JLabel("Válaszd ki a pályát:");
        final JComboBox<String> cb = new JComboBox<>(choices);

        //user level from file
        JLabel label = new JLabel("VAGY");
        final JButton loadFromFileButton = new JButton("<html><font color = white>Egyedi pálya betöltése fájlból</font></html>");
        loadFromFileButton.setBackground(new Color(47,79,79));
        loadFromFileButton.addActionListener((ActionEvent e) -> {
            if (e.getSource() == loadFromFileButton) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File("res/levels"));
                int response = fileChooser.showOpenDialog(null);

                if (response == JFileChooser.APPROVE_OPTION) {
                    userFile = new File(fileChooser.getSelectedFile().getAbsolutePath());
                    cb.setEnabled(false);
                    System.out.println("Selected file: " + userFile);
                }
            }
        });

        final JLabel lp1 = new JLabel("Első játékos neve:");
        final JTextField p1name = new JTextField(20);

        final JLabel lp2 = new JLabel("Második játékos neve:");
        final JTextField p2name = new JTextField(20);

        final JButton startButton = new JButton("Játék indítása");
        startButton.addActionListener((ActionEvent e) -> {
            if (p1name.getText().length() != 0 && p2name.getText().length() != 0) {
                if (userFile == null){
                    window.setPanel(new GameBoardPanel(cb.getSelectedItem().toString(),false, p1name.getText(),p2name.getText(), window));
                } else {
                    window.setPanel(new GameBoardPanel(userFile.toString(),true, p1name.getText(),p2name.getText(), window));
                }
            }
        });

        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.VERTICAL;

        add(l, gbc);
        add(cb, gbc);

        add(label,gbc);
        add(loadFromFileButton,gbc);

        add(lp1, gbc);
        add(p1name, gbc);

        add(lp2, gbc);
        add(p2name, gbc);

        add(startButton, gbc);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(background, 0, 0, 1400, 750, null);
    }
}
