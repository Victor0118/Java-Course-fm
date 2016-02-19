package lab_3;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class Line extends Shape {

	private static final long serialVersionUID = 1L;

	public Line() {
        super();
    }

    public Line(Point beginning, Point end, Color color, int width) {
        super(beginning, end, color, width);
    }

    @Override
    public void draw(Graphics2D g) {
        g.setPaint(getPaint());
        g.setStroke(getStroke());
        g.drawLine(getBeginning().x, getBeginning().y, getEnd().x, getEnd().y);
    }

}
