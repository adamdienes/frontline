package frontline.persistence;

public class SearchNode {
    public Integer distance;
    public Coord prev;
    public boolean visited;

    public SearchNode() {
        this.visited = false;
        this.prev = null;
        this.distance = null;
    }

    @Override
    public String toString() {
        return "SearchNode{" +
                "distance=" + distance +
                ", prev=" + prev +
                ", visited=" + visited +
                '}';
    }
}
