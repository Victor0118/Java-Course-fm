package lab_3;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Stroke;
import java.io.Serializable;

public abstract class Shape implements Serializable{

	private static final long serialVersionUID = 1L;

    private Point beginning;
    private Point end;
    private Paint paint;
    private int width;
    private float desh[] = {1, 0};
    private boolean isSelected;
    
    public Shape() {
        beginning = new Point(0, 0);
        end = new Point(0, 0);
        paint = Color.BLACK;
        width = 1;
        isSelected = false;
    }

    public Shape(Point beginning, Point end, Paint paint, int width) {
        this.beginning = beginning;
        this.end = end;
        this.paint = paint;
        this.width = width;
        this.isSelected = false;
    }


    public void setSelected() {
		this.isSelected = !isSelected;
	}
    public Point getBeginning() {
        return beginning;
    }

    public void setBeginning(Point beginning) {
        this.beginning = beginning;
    }

    public Point getEnd() {
        return end;
    }

    public void setEnd(Point end) {
        this.end = end;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public int getWidth() {
        return width;
    }
    
    public void setDesh() {
		this.desh[0] = 10;
		this.desh[1] = 10;
	}
    
    public Stroke getStroke() {
    	return new BasicStroke(width,BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10.0f,desh, 0f);
	}
    /**
     * Sets the stroke of the shape
     *
     * @param stroke Stroke object
     */
    public void setWidth(int width) {
        this.width = width;
    }

    //@Override
    public abstract void draw(Graphics2D g);

	public boolean isSelected() {
		return isSelected;
		// TODO Auto-generated method stub
	}
}
