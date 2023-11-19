package frontline.persistence;

import frontline.entities.*;
import frontline.res.ResourceLoader;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

import static frontline.entities.NeutralType.Forest;
import static frontline.entities.NeutralType.Swamp;

public class GameEngine {
    public static int PASSIVE_GOLD_GAIN = 50;
    public static int GOLD_GAIN_PER_UNIT = 10;
    private final int timerDelay = 500;
    private final int timelimit = 3000;
    protected Entity[][] statics = new Entity[10][20];
    protected Image img = ResourceLoader.loadImage("graphics/units/multi-unit.png");
    private Player player1;
    private Player player2;
    private ArrayList<MovingEntity> entities = new ArrayList<>();
    private Player currentPlayer;
    private boolean isCustomLevel;
    private String filePath, filename;
    private int turn = 1;
    private int elapsedTime = 0;
    private int signalCount = 0;
    private boolean warPhase = false;

    public GameEngine(String level, boolean isCustomLevel, String player1name, String player2name) {
        player1 = new Player(player1name, Color.BLUE);
        player2 = new Player(player2name, Color.RED);
        this.isCustomLevel = isCustomLevel;
        currentPlayer = player1;

        try {
            loadFromFile(level);
        } catch (IOException ex) {
            System.out.println("File error." + ex);
        }
    }

    public ArrayList<MovingEntity> getEntities() {
        return entities;
    }

    public int getTurn() {
        return turn;
    }

    public void draw(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 20; j++) {
                if (statics[i][j] != null) statics[i][j].draw(g2);
            }
        }
        g2.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
        g2.setFont(new Font("Courier New", Font.BOLD, 20));

        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 20; x++) {
                int counter = 0;
                int red_cavalry = 0;
                int red_warrior = 0;
                int blue_cavalry = 0;
                int blue_warrior = 0;
                MovingEntity lastEntity = null;
                for (MovingEntity entity : entities) {
                    if (entity.getX() == x && entity.getY() == y) {
                        counter++;
                        lastEntity = entity;
                        if (entity instanceof Warrior) {
                            if (entity.getOwner() == player1) {
                                blue_warrior++;
                            } else {
                                red_warrior++;
                            }
                        } else if (entity instanceof Cavalry) {
                            if (entity.getOwner() == player1) {
                                blue_cavalry++;
                            } else {
                                red_cavalry++;
                            }
                        }
                    }
                }
                if (counter == 1) {
                    lastEntity.draw(g2);
                    g2.setColor(Color.black);
                    g2.fillRect(x * 70 + 4, y * 70 + 1, 62, 7);
                    g2.setColor(new Color(123, 23, 33));
                    g2.fillRect(x * 70 + 5, y * 70 + 2, (int) (((double) lastEntity.getCurrentHp()) / lastEntity.getMaxHp() * 60), 5);
                } else if (counter > 1) {
                    g2.drawImage(img, x * 70, y * 70, 70, 70, null);
                    g2.drawString(String.valueOf(blue_warrior), 2 + x * 70, 15 + y * 70);
                    g2.drawString(String.valueOf(blue_cavalry), 2 + x * 70, 65 + y * 70);
                    g2.drawString(String.valueOf(red_warrior), 55 + x * 70, 15 + y * 70);
                    g2.drawString(String.valueOf(red_cavalry), 55 + x * 70, 65 + y * 70);
                }
            }
        }

    }

    private void loadFromFile(String level) throws IOException {
        BufferedReader br = null;
        InputStream fstream = null;

        if (isCustomLevel) {
            filePath = level;
            br = new BufferedReader(new FileReader(filePath));
        } else {
            FileType levelType = FileType.getInstance(level);

            filename = switch (levelType) {
                case Level1 -> "level1.txt";
                case Level2 -> "level2.txt";
                case Level3 -> "level3.txt";
                case Level4 -> "level4.txt";
                case Level5 -> "level5.txt";
                case Level6 -> "level6.txt";
                default -> null;
            };

            fstream = ResourceLoader.loadResource("levels/" + filename);
            br = new BufferedReader(new InputStreamReader(fstream));
        }

        String strLine;
        int rowCtr = 0;
        int cols = statics[0].length;

        while ((strLine = br.readLine()) != null) {
            for (int i = 0; i < cols; i++) {
                StaticsType type = StaticsType.getInstance(strLine.charAt(i));

                switch (type) {
                    case Forest -> statics[rowCtr][i] = new NeutralEntity(i, rowCtr, Forest);
                    case Swamp -> statics[rowCtr][i] = new NeutralEntity(i, rowCtr, Swamp);
                    case Castle1 -> statics[rowCtr][i] = new Castle(i, rowCtr, player1);
                    case Castle2 -> statics[rowCtr][i] = new Castle(i, rowCtr, player2);
                    case Barrack1 -> statics[rowCtr][i] = new Barrack(i, rowCtr, player1);
                    case Barrack2 -> statics[rowCtr][i] = new Barrack(i, rowCtr, player2);
                    case Empty -> statics[rowCtr][i] = null;
                }
            }
            rowCtr++;
        }
        if (!isCustomLevel) fstream.close();
    }

    public Entity getEntity(int i, int j) {
        return statics[i][j];
    }

    public void setEntity(int i, int j, Entity entity) {
        statics[i][j] = entity;
    }

    public boolean getWarPhase() {
        return warPhase;
    }

    public void setWarPhase(boolean w) {
        warPhase = w;
    }

    public void addToEntities(MovingEntity e) {
        entities.add(e);
    }

    public boolean validateClick(int j) {
        return (currentPlayer == player1 && j < 10) || (currentPlayer == player2 && j > 9);
    }

    private int countUnits(Player player) {
        int counter = 0;
        for (MovingEntity movingEntity : entities) {
            if (movingEntity.getOwner() == player) {
                counter++;
            }
        }
        return counter;
    }

    public void endTurn() {
        if (currentPlayer == player1) {
            currentPlayer = player2;
        } else {
            currentPlayer = player1;
        }
        if (turn % 2 == 0) {
            player1.setGold(player1.getGold() + PASSIVE_GOLD_GAIN + countUnits(player1) * GOLD_GAIN_PER_UNIT);
            player2.setGold(player2.getGold() + PASSIVE_GOLD_GAIN + countUnits(player2) * GOLD_GAIN_PER_UNIT);
        }
        turn++;

    }

    public String[] getPlayersLabel() {

        return new String[]{player1.getName() + ": " + player1.getGold() + " arany, " + player1.getHp() + " hp",
                player2.getName() + ": " + player2.getGold() + " arany, " + player2.getHp() + " hp"};
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public String getTurnLabel() {
        if (turn % 2 == 0) {
            return player2.getName() + " köre";
        }
        return player1.getName() + " köre";
    }

    public String handleBuild(int i, int j, String tower) throws IllegalArgumentException {
        Entity clickedStatic = statics[i][j];
        if (!(clickedStatic == null && validateClick(j)))
            throw new IllegalArgumentException("Invalid build");

        String message = null;
        boolean valid = true;
        for (Entity e : entities) {
            if (e.getX() == j && e.getY() == i) {
                valid = false;
                break;
            }
        }
        if (valid) {
            TowerType towerType = TowerType.getInstance(tower);
            Tower newTower = TowerFactory.makeTower(towerType, i, j, currentPlayer);
            message = buildTower(newTower, i, j);
        } else {
            message = "Az adott hely foglalt!";
        }
        return message;
    }

    public String handleRecruit(int i, int j, String unit) throws IllegalArgumentException {
        Entity clickedStatic = statics[i][j];
        if (!((clickedStatic instanceof Barrack || clickedStatic instanceof Castle) && ((StaticEntity) clickedStatic).getOwner() == currentPlayer))
            throw new IllegalArgumentException("Invalid recruit");

        String message = null;
        ArrayList<Coord> adjacentFields = getAdjacentFields(new Coord(j, i), Radius.Range1, false);
        ArrayList<Coord> coords = new ArrayList<>();
        for (Coord coord : adjacentFields) {
            if (isPathBetweenCoords(coord, getEnemyCastle(getCurrentPlayer()))) {
                coords.add(coord);
            }
        }
        if (coords.size() > 0) {
            Coord placeSlot = getClosestCoordToEnemyCastle(coords);
            UnitType unitType = UnitType.getInstance(unit);
            MovingEntity newUnit = UnitFactory.makeUnit(unitType, placeSlot.x, placeSlot.y, currentPlayer);
            message = recruitUnit(newUnit);
        } else {
            message = "Nincs hely a toborzáshoz!";
        }
        return message;
    }

    private boolean isForest(Entity entity) {
        return entity instanceof NeutralEntity && ((NeutralEntity) entity).getType() == Forest;
    }

    private boolean validCords(int x, int y) {
        return x >= 0 && x <= 19 && y >= 0 && y <= 9;
    }

    private ArrayList<Coord> getAdjacentFields(Coord cord, Radius rad, boolean includeForests) {

        ArrayList<Coord> adjacents = new ArrayList<>();

        int radius = rad == Radius.Range2 ? 2 : 1;
        for (int x = cord.x - radius; x <= cord.x + radius; x++) {
            for (int y = cord.y - radius; y <= cord.y + radius; y++) {
                if (Math.abs(cord.x - x) == Math.abs(cord.y - y) && rad == Radius.Cornerless) continue;

                if (validCords(x, y)) {
                    if (statics[y][x] == null || (includeForests && isForest(statics[y][x]))) {
                        adjacents.add(new Coord(x, y));
                    }
                }
            }
        }
        return adjacents;
    }

    private ArrayList<Coord> findPath(Coord start, Coord end, boolean includeForests) {
        ArrayList<Coord> queue = new ArrayList<>();
        SearchNode[][] searchNodes = new SearchNode[10][20];
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 20; x++) {
                searchNodes[y][x] = new SearchNode();
            }
        }
        queue.add(start);
        searchNodes[start.y][start.x].distance = 0;
        searchNodes[start.y][start.x].visited = true;
        while (!queue.isEmpty()) {
            SearchNode current = searchNodes[queue.get(0).y][queue.get(0).x];
            ArrayList<Coord> adjacents = getAdjacentFields(queue.get(0), Radius.Cornerless, includeForests);
            for (Coord adjacent : adjacents) {
                if (!searchNodes[adjacent.y][adjacent.x].visited) {
                    searchNodes[adjacent.y][adjacent.x].visited = true;
                    searchNodes[adjacent.y][adjacent.x].distance = current.distance + 1;
                    searchNodes[adjacent.y][adjacent.x].prev = new Coord(queue.get(0).x, queue.get(0).y);
                    queue.add(new Coord(adjacent.x, adjacent.y));
                }
            }
            queue.remove(0);
        }
        ArrayList<Coord> path = new ArrayList<>();

        ArrayList<Coord> adjacents = getAdjacentFields(end, Radius.Cornerless, includeForests);
        if (adjacents.isEmpty()) {
            return path;
        }

        Coord newEnd = null;


        // choosing closeset adjacent tile
        for (Coord adjacent : adjacents) {
            if (newEnd == null && searchNodes[adjacent.y][adjacent.x].visited) {
                newEnd = adjacent;
            } else if (newEnd != null && searchNodes[adjacent.y][adjacent.x].visited && searchNodes[adjacent.y][adjacent.x].distance < searchNodes[newEnd.y][newEnd.x].distance) {
                newEnd = adjacent;
            }
        }

        if (newEnd == null) {
            return path;
        }


        path.add(newEnd);
        while (searchNodes[path.get(path.size() - 1).y][path.get(path.size() - 1).x].prev != null) {
            path.add(searchNodes[path.get(path.size() - 1).y][path.get(path.size() - 1).x].prev);
        }
        Collections.reverse(path);
        path.remove(0);
        return path;
    }

    private boolean isPathBetweenCastles() {
        return isPathBetweenCoords(getEnemyCastle(player1), getEnemyCastle(player2));
    }

    private boolean isPathBetweenCoords(Coord start, Coord end) {
        return !findPath(start, end, false).isEmpty();
    }

    private Coord getEnemyCastle(Player player) {
        for (Entity[] staticRow : statics) {
            for (Entity staticEntity : staticRow) {
                if (staticEntity instanceof Castle && ((Castle) staticEntity).getOwner() != player) {
                    return new Coord(staticEntity.getX(), staticEntity.getY());
                }
            }
        }
        return null;
    }

    private boolean hasAdjacentEnemyCastle(Coord cord, Player player) {
        Coord enemyCastleCoord = getEnemyCastle(player);
        if (enemyCastleCoord.x == cord.x + 1 && enemyCastleCoord.y == cord.y) {
            return true;
        }
        if (enemyCastleCoord.x == cord.x - 1 && enemyCastleCoord.y == cord.y) {
            return true;
        }
        if (enemyCastleCoord.x == cord.x && enemyCastleCoord.y == cord.y + 1) {
            return true;
        }
        return enemyCastleCoord.x == cord.x && enemyCastleCoord.y == cord.y - 1;
    }

    public void resetWarData() {
        elapsedTime = 0;
        signalCount = 0;
        warPhase = false;
    }

    public void handleTick() {
        elapsedTime += timerDelay;

        signalCount++;
        // handle entity movement
        ArrayList<Integer> toRemoveIndexes = new ArrayList<>();
        for (MovingEntity entity : entities) {
            if (signalCount % entity.getSignalsToInvoke() == 0) {
                boolean includeForests = entity instanceof Warrior;
                ArrayList<Coord> path = findPath(new Coord(entity.getX(), entity.getY()), getEnemyCastle(entity.getOwner()), includeForests);
                if (path.isEmpty()) {
                    if (hasAdjacentEnemyCastle(new Coord(entity.getX(), entity.getY()), entity.getOwner())) {
                        toRemoveIndexes.add(entities.indexOf(entity));
                        // toRemove.add(entity);
                        if (entity.getOwner() == player1) {
                            player2.setHp(player2.getHp() - entity.attack());

                        } else {
                            player1.setHp(player1.getHp() - entity.attack());
                        }

                    }
                    continue;
                }
                Coord newCords = path.get(0);
                entity.setX(newCords.x);
                entity.setY(newCords.y);
                // System.out.println(entity + " to " + newCords);
            }
        }
        for (int i = 0; i < toRemoveIndexes.size(); i++) {
            int index = toRemoveIndexes.get(i) - i;
            entities.remove(index);
        }
        // handle tower shooting
        toRemoveIndexes = new ArrayList<>();
        for (Entity[] entityRow : statics) {
            for (Entity entity : entityRow) {
                if (entity instanceof Tower && signalCount % ((Tower) entity).getSignalsToInvoke() == 0) {
                    Radius radius = ((Tower) entity).getLevel() == 1 ? Radius.Range1 : Radius.Range2;
                    for (Coord cord : getAdjacentFields(new Coord(entity.getX(), entity.getY()), radius, true)) {
                        for (MovingEntity movingEntity : entities) {
                            if (movingEntity.getX() == cord.x && movingEntity.getY() == cord.y && ((Tower) entity).getOwner() != movingEntity.getOwner()) {
                                movingEntity.setCurrentHp(movingEntity.getCurrentHp() - ((Tower) entity).getDamage());
                                if (movingEntity.getCurrentHp() <= 0 && !toRemoveIndexes.contains(entities.indexOf(movingEntity))) {
                                    toRemoveIndexes.add(entities.indexOf(movingEntity));
                                }
                            }
                        }
                    }
                }
            }
        }
        for (int i = 0; i < toRemoveIndexes.size(); i++) {
            int index = toRemoveIndexes.get(i) - i;
            entities.remove(index);
        }
    }

    public int getTimerDelay() {
        return timerDelay;
    }

    public int getTimelimit() {
        return timelimit;
    }

    public int getElapsedTime() {
        return elapsedTime;
    }

    private int getPathLength(Coord coord) {
        return findPath(coord, getEnemyCastle(currentPlayer), false).size();
    }

    private Coord getClosestCoordToEnemyCastle(ArrayList<Coord> coords) {
        int minPathLength = getPathLength(coords.get(0));
        Coord minPathCoord = coords.get(0);
        for (Coord coord : coords) {
            if (getPathLength(coord) < minPathLength) {
                minPathLength = getPathLength(coord);
                minPathCoord = coord;
            }
        }
        return minPathCoord;
    }

    private boolean isValidTowerBuild() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 20; j++) {
                if (statics[i][j] instanceof Barrack) {
                    ArrayList<Coord> adjacents = getAdjacentFields(new Coord(j, i), Radius.Range1, true);
                    boolean validBarrack = false;
                    for (Coord coord : adjacents) {
                        if (isPathBetweenCoords(coord, getEnemyCastle(currentPlayer))) validBarrack = true;
                    }
                    if (!validBarrack) return false;
                }
            }
        }
        return isPathBetweenCastles();
    }

    private String buildTower(Tower tower, int i, int j) {
        int cost = tower.getCost();
        if (currentPlayer.canAfford(cost)) {
            statics[i][j] = tower;
            if (isValidTowerBuild()) {
                currentPlayer.pay(cost);
            } else {
                statics[i][j] = null;
                return "Nem lehet a kastélyok/barakkok közötti utat elzárni!";
            }
        } else {
            return "Nincs elegendő pénz a torony építéséhez!";
        }
        return null;
    }

    public String gameEnds() {

        if (player1.getHp() <= 0) {
            return player2.getName();
        }
        if (player2.getHp() <= 0) {
            return player1.getName();
        }
        return null;
    }

    private String recruitUnit(MovingEntity entity) {
        int cost = entity.getCost();
        if (currentPlayer.canAfford(cost)) {
            currentPlayer.pay(cost);
            entities.add(entity);
        } else {
            return "Nincs elegendő pénz a toborzáshoz!";
        }
        return null;
    }
}
