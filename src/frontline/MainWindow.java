package frontline;

import frontline.panels.MenuPanel;
import frontline.res.ResourceLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainWindow extends JFrame {
    private JPanel mainPanel;

    public MainWindow() {
        // menu items
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Játék");
        JMenuItem returnToMainMenu = new JMenuItem("Főmenü");
        JMenuItem exitGame = new JMenuItem("Kilépés");
        returnToMainMenu.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int a = JOptionPane.showConfirmDialog(MainWindow.this, "Biztosan kilép a főmenübe?", "Főmenü",
                        JOptionPane.YES_NO_OPTION);
                if (a == JOptionPane.YES_OPTION) {
                    setPanel(new MenuPanel(MainWindow.this));
                }

            }
        });

        exitGame.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        gameMenu.add(returnToMainMenu);
        gameMenu.add(exitGame);
        menuBar.add(gameMenu);
        setJMenuBar(menuBar);

        // window preferentials
        setSize(new Dimension(1400, 750));
        setTitle("Frontline");
        Image icon = ResourceLoader.loadImage("graphics/icon.jpg");
        setIconImage(icon);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        mainPanel = new MenuPanel(this);
        getContentPane().add(mainPanel);
        pack();
        setVisible(true);
    }

    public void setPanel(JPanel p) {
        getContentPane().removeAll();
        mainPanel = p;
        getContentPane().add(mainPanel);
        pack();
        repaint();
    }
}
