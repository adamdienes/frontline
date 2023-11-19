package frontline.panels;

import frontline.MainWindow;
import frontline.res.ResourceLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MenuPanel extends JPanel {
    private final Image background = ResourceLoader.loadImage("graphics/menu_background.png");

    public MenuPanel(MainWindow window) {
        JButton newGameButton = new JButton("Új játék indítása");
        JButton levelEditor = new JButton("Pályaszerkesztő");
        JButton exitButton = new JButton("Kilépés");

        setPreferredSize(new Dimension(1400, 750));
        newGameButton.setPreferredSize(new Dimension(200, 80));
        levelEditor.setPreferredSize(new Dimension(200, 80));
        exitButton.setPreferredSize(new Dimension(200, 80));

        newGameButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.setPanel(new NewGamePanel(window));
            }
        });
        levelEditor.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.setPanel(new LevelEditorPanel());
            }
        });
        exitButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.VERTICAL;

        add(newGameButton, gbc);
        add(levelEditor, gbc);
        add(exitButton, gbc);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(background, 0, 0, 1400, 750, null);
    }

}
