package lab_3;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class Rectangle extends Shape {

	private static final long serialVersionUID = 1L;

	public Rectangle() {
        super();
    }

    public Rectangle(Point beginning, Point end, Color color, int width) {
        super(beginning, end, color, width);
    }

    @Override
    public void draw(Graphics2D g) {
        g.setPaint(getPaint());
        g.setStroke(getStroke());

        g.drawRect(getBeginning().x > getEnd().x ? getEnd().x : getBeginning().x,
                getBeginning().y > getEnd().y ? getEnd().y : getBeginning().y,
                Math.abs(getEnd().x - getBeginning().x),
                Math.abs(getEnd().y - getBeginning().y));
    }

}
