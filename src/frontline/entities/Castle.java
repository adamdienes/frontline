package frontline.entities;

import frontline.persistence.Player;
import frontline.res.ResourceLoader;

import java.awt.*;

public class Castle extends StaticEntity {
    public Castle(int x, int y, Player owner) {
        super(x, y, owner);
        if (owner.getColor() == Color.BLUE) {
            this.img = ResourceLoader.loadImage("graphics/buildings/castles/castle_blue.png");
        } else {
            this.img = ResourceLoader.loadImage("graphics/buildings/castles/castle_red.png");
        }
    }

    @Override
    public int getSignalsToInvoke() {
        return 1;
    }

    @Override
    public String toString() {
        return "Castle{" +
                "owner=" + owner +
                ", x=" + x +
                ", y=" + y +
                '}';
    }

}
