package lab_3;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import javax.swing.JOptionPane;

public class Text extends Shape {

	private static final long serialVersionUID = 1L;
	
	private final String text;
    private final int x;
	private final int y;
    private final Paint paint;


    public Text(int x, int y, Paint paint) {
        this.x = x;
        this.y = y;
        this.paint = paint;
        
        String input = JOptionPane.showInputDialog("Input something: ");
        this.text = input;
        
    }
    
    public String getText() {
		return text;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
    
    
    public void draw(Graphics2D g) {
        g.setColor((Color) paint);
        if (text != null ) {
            g.drawString(text, x, y);
		}
    }

}
