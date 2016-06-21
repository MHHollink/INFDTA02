package nl.mehh.dta.assignment1.cluster.data;

/**
 * TODO: Write class level documentation
 *
 * @author Marcel
 * @since 21-6-2016.
 */
public class Point {

    Centroid linked;
    double x;
    double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setLinked(Centroid linked) {
        this.linked = linked;
    }

    public Centroid getLinked() {
        return linked;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
