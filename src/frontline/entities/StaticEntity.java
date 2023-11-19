package frontline.entities;

import frontline.persistence.Player;
import frontline.res.ResourceLoader;

public abstract class StaticEntity extends ControlledEntity {

    public StaticEntity(int x, int y, Player owner) {
        super(x, y, owner);
        this.img = ResourceLoader.loadImage("graphics/buildings/towers/brick_tower_blue.png");
    }

}
