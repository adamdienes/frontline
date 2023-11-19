package frontline.entities;

import frontline.persistence.Player;

public abstract class MovingEntity extends ControlledEntity {
    protected int maxHp;
    protected int currentHp;

    public MovingEntity(int x, int y, Player owner) {
        super(x, y, owner);
    }

    public int attack() {
        System.out.println(this + " attacks");
        return 2;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public void setCurrentHp(int currentHp) {
        this.currentHp = currentHp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public abstract int getCost();
}
