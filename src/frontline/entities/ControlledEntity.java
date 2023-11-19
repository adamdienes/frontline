package frontline.entities;

import frontline.persistence.Player;

public abstract class ControlledEntity extends Entity {
    protected Player owner;

    public ControlledEntity(int x, int y, Player owner) {
        super(x, y);
        this.owner = owner;
    }

    public abstract int getSignalsToInvoke();

    public Player getOwner() {
        return owner;
    }
}
