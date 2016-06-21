package nl.mehh.dta.assignment1.cluster.data;

import com.sun.istack.internal.NotNull;
import nl.mehh.dta.assignment1.cluster.util.CentroidColors;

/**
 * TODO: Write class level documentation
 *
 * @author Marcel
 * @since 21-6-2016.
 */
public class Centroid extends Point {

    private CentroidColors color;

    public Centroid(CentroidColors color, @NotNull Point p) {
        super(p.getX(), p.getY());
        this.color = color;
    }

    public CentroidColors getColor() {
        return color != null ? color : null;
    }

    @Override
    public String toString() {
        return "Centroid{" +
                "color=" + color +
                "}" + super.toString();
    }

    public void setPoint(double x, double y) {
        setX(x);
        setY(y);
    }
}