package frontline.panels;

import frontline.MainWindow;
import frontline.entities.*;
import frontline.persistence.GameEngine;
import frontline.res.ResourceLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameBoardPanel extends JPanel {
    private final JPanel gameStatus = new JPanel();
    private final JLabel player1Label = new JLabel("", SwingConstants.CENTER);
    private final JLabel player2Label = new JLabel("", SwingConstants.CENTER);
    private final JLabel turnLabel = new JLabel("", SwingConstants.CENTER);
    private GameEngine engine;
    private MainWindow window;

    public GameBoardPanel(String level, boolean isCustomLevel, String player1name, String player2name, MainWindow window) {
        setLayout(new BorderLayout());
        engine = new GameEngine(level, isCustomLevel, player1name, player2name);
        this.window = window;
        setPreferredSize(new Dimension(1400, 750));

        updateGameStatus();

        // Setup game status bar
        gameStatus.setPreferredSize(new Dimension(1400, 50));
        gameStatus.setOpaque(true);
        gameStatus.setBackground(Color.GRAY);
        gameStatus.setLayout(new GridLayout(1, 4));
        // Add player labels
        gameStatus.add(player1Label);
        gameStatus.add(player2Label);
        // Add turn label
        gameStatus.add(turnLabel);

        // Add end turn button
        JButton endTurnButton = new JButton("Kör befejezése");
        endTurnButton.addActionListener(e -> {
            if (!engine.getWarPhase()) {
                engine.endTurn();
                if (engine.getTurn() % 2 == 1) {
                    handleWarPhase();
                    GameBoardPanel.this.updatePlayerStatus();

                } else {
                    GameBoardPanel.this.updateGameStatus();
                }

                GameBoardPanel.this.repaint();
            }
        });
        gameStatus.add(endTurnButton);
        add(gameStatus, BorderLayout.SOUTH);

        MouseAdapter clickHandler = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!engine.getWarPhase()) {
                    int i = e.getY() / Entity.SIZE;
                    int j = e.getX() / Entity.SIZE;
                    if (e.getY() < 700) {
                        Entity clicked = engine.getEntity(i, j);
                        if (clicked == null && engine.validateClick(j)) {
                            String[] turretTypes = {"Vastorony(" + IronTower.COST + " arany)", "Téglatorony(" + BrickTower.COST + " arany)", "Fatorony(" + WoodenTower.COST + " arany)"};
                            String tower = (String) JOptionPane.showInputDialog(
                                    GameBoardPanel.this,
                                    "Válassza ki a területre építeni kívánt tornyot",
                                    "Torony építése",
                                    JOptionPane.PLAIN_MESSAGE,
                                    null,
                                    turretTypes,
                                    turretTypes[2]
                            );
                            if (tower != null) {
                                try {
                                    String message = engine.handleBuild(i, j, tower);
                                    if (message != null) {
                                        JOptionPane.showMessageDialog(GameBoardPanel.this, message);
                                    }
                                } catch (Exception ex) {
                                    System.out.println(ex.getMessage());
                                }
                            }
                        } else if ((clicked instanceof Barrack || clicked instanceof Castle) && ((StaticEntity) clicked).getOwner() == engine.getCurrentPlayer()) {

                            String[] entityTypes = (clicked instanceof Barrack) ? new String[]{"Lovasság(" + Cavalry.COST + " arany)", "Gyalogság(" + Warrior.COST + " arany)"} : new String[]{"Gyalogság(" + Warrior.COST + " arany)"};

                            String unit = (String) JOptionPane.showInputDialog(
                                    GameBoardPanel.this,
                                    "Válassza ki a toborozni kívánt egységet",
                                    "Egység toborzása",
                                    JOptionPane.PLAIN_MESSAGE,
                                    null,
                                    entityTypes,
                                    "Gyalogság(" + Warrior.COST + " arany)"
                            );
                            if (unit != null) {
                                try {
                                    String message = engine.handleRecruit(i, j, unit);
                                    if (message != null) {
                                        JOptionPane.showMessageDialog(GameBoardPanel.this, message);
                                    }
                                } catch (Exception ex) {
                                    System.out.println(ex.getMessage());
                                }

                            }
                        } else if (clicked instanceof Tower && engine.validateClick(j)) {
                            Object[] options = ((Tower) clicked).getLevel() == 1 ? new String[]{"Torony lebontása", "Torony fejlesztése(100 arany)"} : new String[]{"Torony lebontása"};
                            int option = JOptionPane.showOptionDialog(GameBoardPanel.this,
                                    clicked + "\nVálassza ki a kívánt műveletet?",
                                    "Torony műveletek",
                                    JOptionPane.DEFAULT_OPTION,
                                    JOptionPane.QUESTION_MESSAGE,
                                    null,
                                    options,
                                    options[0]);
                            if (option == 1) {
                                ((Tower) clicked).upgrade();
                                System.out.println(clicked);
                            } else if (option == 0) {
                                engine.getCurrentPlayer().pay(-1 * ((Tower) clicked).destroyValue());
                                engine.setEntity(i, j, null);
                            }
                        }

                        repaint();
                        updateGameStatus();
                    }
                }
            }
        };
        addMouseListener(clickHandler);
    }

    public void updateGameStatus() {
        String[] playerLabels = engine.getPlayersLabel();
        player1Label.setText(playerLabels[0]);
        player2Label.setText(playerLabels[1]);
        turnLabel.setText(engine.getTurnLabel());
    }

    public void updatePlayerStatus() {
        String[] playerLabels = engine.getPlayersLabel();
        player1Label.setText(playerLabels[0]);
        player2Label.setText(playerLabels[1]);
    }

    public void handleWarPhase() {
        engine.setWarPhase(true);
        turnLabel.setText("Háború!");
        repaint();

        Timer timer = new Timer(engine.getTimerDelay(), (ActionEvent ae) -> {
            engine.handleTick();
            updatePlayerStatus();
            repaint();
            String gameEnds = engine.gameEnds();
            if (gameEnds != null) {
                System.out.println(gameEnds);
                window.setPanel(new EndGamePanel(window, gameEnds + " játékos nyert!"));
                Timer s = (Timer) ae.getSource();
                s.stop();
            }

            if (engine.getElapsedTime() >= engine.getTimelimit()) {
                Timer s = (Timer) ae.getSource();
                s.stop();
                engine.resetWarData();
                updateGameStatus();
                repaint();
            }
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g2);
        g2.drawImage(ResourceLoader.loadImage("graphics/terrains/grass_background.png"), 0, 0, 1400, 700, null);
        engine.draw(g2);
    }
}