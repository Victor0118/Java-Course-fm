package lab_3;



import java.awt.*;
import java.awt.event.*;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Panel extends JPanel{

	private  Shapes shapes;
    private  Shapes redoShapes;
    private  Shapes selectShapes;
  
	private Shape currentShape;
  
	private int shapeType;
    private Paint currentColor;
    private int width;
    private String text;

    public Panel(JLabel statusLabel) {
        this.shapes = new Shapes(); //No limit on the number of the shapes
        this.redoShapes = new Shapes();
        this.selectShapes = new Shapes();
        this.currentShape = null;
        this.shapeType = 0;
        this.currentColor = Color.BLACK;
        this.text = "";
        this.setBackground(Color.WHITE);
        
        ClickHandler handler = new ClickHandler();
        this.addMouseListener(handler);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        shapes.stream().forEach((shape) -> {    // Java 8 streams
            shape.draw((Graphics2D) g);
        });
        selectShapes.stream().forEach((shape) -> {    // Java 8 streams
            shape.draw((Graphics2D) g);
        });
    }

   
    
    public Shapes getShapes() {
  		return shapes;
  	}
    
    public Shapes setShapes(Shapes newShapes) {
    	clearDrawing();
		this.shapes = newShapes;
		paintComponent(getGraphics());
  		return shapes;
  	}
    
    public void setShapeType(int shapeType) {
        this.shapeType = shapeType;
    }

    public void setCurrentColor(Paint currentColor) {
        this.currentColor = currentColor;
    }

	public void setWidth(int value) {
		this.width = value;
		// TODO Auto-generated method stub
		
	}
  
    public void setText(String text) {
        this.text = text;
    }

    public void clearLastShape() {
        if (!shapes.isEmpty()) {
            Shape shape = shapes.remove(shapes.size() - 1);
            redoShapes.add(shape);
        }
        repaint();
    }

    public void redoLastShape() {
        if (!redoShapes.isEmpty()) {
            Shape shape = redoShapes.remove(redoShapes.size() - 1);
            shapes.add(shape);
        }
        repaint();
    }

    public void clearDrawing() {
        while (!shapes.isEmpty()) {
            Shape shape = shapes.remove(0);
            redoShapes.add(shape);
        }
        repaint();
    }

    

    
    private class ClickHandler extends MouseAdapter {
    	
    	 @Override
    	 public void mousePressed(MouseEvent e) {
    		 
     		//reset selected
    		selectShapes.clear();
    		for (Shape shape : shapes) {
				if (shape.isSelected()) {
					shape.setSelected();
				}
			}
    		
    		if (shapeType == 3) {
    			 currentShape = new Text( e.getX(), e.getY(), currentColor);
    			 shapes.add(currentShape);
    			 currentShape = null;
 	             repaint();
			}
    		else if (shapeType == 4) {
    			
    			for (Shape shape : shapes) {
    				
    				Point begining = shape.getBeginning();
    				Point end = shape.getEnd();
    				
    				if (shape instanceof Text) {
    					
	    				
    					double smallx = ((Text) shape).getX() -10;
	    				double smally = ((Text) shape).getY() -20;
	    				double bigx = ((Text) shape).getX() + ((Text) shape).getText().length()*7.7;
	    				double bigy = ((Text) shape).getY() +15;
	    				
    					if (e.getX()>smallx&&e.getX()<bigx&&e.getY()>smally&&e.getY()<bigy) {
							Shape re = new Rectangle();
							((Shape) re).setBeginning(new Point((int) smallx,(int) smally));
							((Shape) re).setEnd(new Point((int) bigx,(int) bigy));
			                ((Shape) re).setWidth(1);
			                ((Shape) re).setPaint(currentColor);
			                ((Shape) re).setDesh();
    						selectShapes.add(re);
							shape.setSelected();
						}
					}
	    			else {
	    				double bigx = begining.getX()>end.getX()?begining.getX():end.getX();
	    				double smallx = begining.getX()>end.getX()?end.getX():begining.getX();
	    				double bigy = begining.getY()>end.getY()?begining.getY():end.getY();
	    				double smally = begining.getY()>end.getY()?end.getY():begining.getY();

	    				if (shape instanceof Line) {
	    					if (e.getX()>smallx&&e.getX()<bigx&&e.getY()>smally&&e.getY()<bigy) {
	    						
	    						//System.out.println(Math.abs(((end.getY()-begining.getY())*(e.getX()-begining.getX())-(end.getX()-begining.getX())*(e.getY()-begining.getY()))));
	    						if (Math.abs(((end.getY()-begining.getY())*(e.getX()-begining.getX())-(end.getX()-begining.getX())*(e.getY()-begining.getY())))<10000) {
	    							Shape re = new Rectangle();
									((Shape) re).setBeginning(begining);
									((Shape) re).setEnd(end);
					                ((Shape) re).setWidth(1);
					                ((Shape) re).setPaint(currentColor);
					                ((Shape) re).setDesh();
		    						selectShapes.add(re);
									shape.setSelected();
								}
								
							}
						}
	    				else if (shape instanceof Rectangle) {
	    					if (e.getX()>smallx&&e.getX()<bigx&&e.getY()>smally&&e.getY()<bigy) {
	    						
	    						Shape re = new Rectangle();
								((Shape) re).setBeginning(new Point((int) smallx-10,(int) smally-10));
								((Shape) re).setEnd(new Point((int) bigx+10,(int) bigy+10));
				                ((Shape) re).setWidth(1);
				                ((Shape) re).setPaint(currentColor);
				                ((Shape) re).setDesh();
				                selectShapes.add(re);
								shape.setSelected();
				                
	    					}
						}
	    				else {
							if (e.getX()>smallx&&e.getX()<bigx&&e.getY()>smally&&e.getY()<bigy) {
								Shape re = new Rectangle();
								((Shape) re).setBeginning(begining);
								((Shape) re).setEnd(end);
				                ((Shape) re).setWidth(1);
				                ((Shape) re).setPaint(currentColor);
				                ((Shape) re).setDesh();
	    						selectShapes.add(re);
								shape.setSelected();
							}
						}
					}
				}
    			repaint();
				
			}
			else if (currentShape == null) {
    			 switch (shapeType) {
		             case 0:
		                 text = "";
		                 currentShape = new Line();
		                 break;
		             case 1:
		                 text = "";
		                 currentShape = new Oval();
		                 break;
		             case 2:
		                 text = "";
		                 currentShape = new Rectangle();
		                 break;
		             
		         }
                ((Shape) currentShape).setBeginning(new Point(e.getX(), e.getY()));
                ((Shape) currentShape).setWidth(width);
                ((Shape) currentShape).setPaint(currentColor);
			}
    		else {
    			 	if (currentShape instanceof Shape) {
    	                ((Shape) currentShape).setEnd(new Point(e.getX(), e.getY()));
    	            }
    	            shapes.add(currentShape);
    	            currentShape = null;
    	            repaint();
    	            
			}
	    		 
       }
    	
	}

	public void delSelection() {
		selectShapes.clear();
		Shapes copy = new Shapes(shapes);
		for (Shape shape : shapes) {
			if (shape.isSelected()) {
				copy.remove(shape);
				redoShapes.add(shape);
			}
		}
		shapes = new Shapes(copy);
		repaint();
		// TODO Auto-generated method stub
		
	}


}
