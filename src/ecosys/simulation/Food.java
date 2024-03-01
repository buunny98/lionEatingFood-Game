package ecosys.simulation;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import main.LionPanel;

public class Food extends SimulationObject {

	private Ellipse2D.Double foodShape; // geometric shape
	private Color foodColor; // shape color
	//------------------------
	private Ellipse2D.Double tail;

	private Ellipse2D.Double eye;
	private Line2D.Double smile;
	//------------------------
	public Food(float x, float y, float size) {
		super(x, y, 10, 10, size);
		this.foodColor = Color.BLUE;
	}

	@Override
	public void draw(Graphics2D g) {
		AffineTransform at = g.getTransform();

		g.translate(pos.x, pos.y);
		g.scale(size, size);

		// draw food
		g.setColor(foodColor);
		g.fill(foodShape);
		
		g.setColor(new Color(0, 0, 180));
        g.fill(tail);
        
        g.setColor(Color.black);
        g.fill(eye);
        g.draw(smile);
		g.setTransform(at);
		//System.out.println(dim.width/2);

		drawInfo(g);
	}

	@Override
	protected void setShapeAttributes() {
		this.foodShape = new Ellipse2D.Double(-dim.width / 2, -dim.height / 2, dim.width + dim.width, dim.height + 3);
		this.tail = new Ellipse2D.Double(dim.width - 2, -dim.height / 2 + 2, dim.width + 3, dim.height - 1);
		this.eye = new Ellipse2D.Double(0, -dim.height / 2 + 3, dim.width/2 - 2, dim.height/2 -2);
		this.smile = new Line2D.Double(-dim.width / 2 + 1, dim.height / 2 - 1, dim.width/2 - 2, dim.height/2 -2);
	}

	@Override
	public void setOutline() {
		outline = new Area(foodShape);
	}

	@Override
	public Shape getOutline() {
		AffineTransform at = new AffineTransform();
		at.translate(pos.x, pos.y);
		at.scale(size, size);
		return at.createTransformedShape(outline);
	}

	@Override
	public void update(ArrayList<SimulationObject> objList, LionPanel panel) {
		// nothing, food don't need to be updated
	}

	public void drawInfo(Graphics2D g) {

	}

}
