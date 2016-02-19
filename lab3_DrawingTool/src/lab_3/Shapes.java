package lab_3;

import java.io.Serializable;
import java.util.LinkedList;

public class Shapes extends LinkedList<Shape> implements Serializable {

	private static final long serialVersionUID = 1L;
	public Shapes(){
		super();
	}
	public Shapes(Shapes shapes) {
		for (Shape shape : shapes) {
			this.add(shape);
		}
		// TODO Auto-generated constructor stub
	}

}
