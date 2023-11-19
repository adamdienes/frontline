package frontline.panels;

import frontline.MainWindow;
import frontline.res.ResourceLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class EndGamePanel extends JPanel {
    private final Image background = ResourceLoader.loadImage("graphics/menu_background.png");
    private String text;

    public EndGamePanel(MainWindow window, String text) {
        this.text = text;
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 40));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(1400, 750));
        setLayout(new BorderLayout());
        add(label, BorderLayout.CENTER);
        setPreferredSize(new Dimension(1400, 750));


        JButton returnButton = new JButton("Vissza a főmenübe");
        returnButton.setPreferredSize(new Dimension(200, 100));
        returnButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.setPanel(new MenuPanel(window));
            }
        });

        add(returnButton, BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(background, 0, 0, 1400, 750, null);
    }
}