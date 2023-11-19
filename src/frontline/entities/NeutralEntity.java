package frontline.entities;

import frontline.res.ResourceLoader;

public class NeutralEntity extends Entity {
    protected NeutralType type;

    public NeutralEntity(int x, int y, NeutralType type) {
        super(x, y);
        this.type = type;
        if (this.type == NeutralType.Forest) {
            this.img = ResourceLoader.loadImage("graphics/terrains/forest.png");

        } else if (this.type == NeutralType.Swamp) {
            this.img = ResourceLoader.loadImage("graphics/terrains/swamp.png");
        }
    }

    @Override
    public String toString() {
        return "NeutralEntity{" +
                "x=" + x +
                ", y=" + y +
                ", type=" + type +
                '}';
    }

    public NeutralType getType() {
        return type;
    }
}
