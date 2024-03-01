package ecosys.simulation;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import main.LionPanel;
import processing.core.PVector;
import util.Util;

public class Prey extends Lion {
	public Prey(float x, float y, float size) {
		super(x, y, 20, 10, size);
		this.color = Util.randomColor();
	}

	@Override
	protected void setShapeAttributes() {
//		head = new Arc2D.Double(0, -dim.height / 2, dim.width / 2, dim.height, 30, 300, Arc2D.PIE);
//		eye = new Ellipse2D.Double(dim.width / 4, -dim.height / 4, dim.width / 20, dim.width / 20);
////
//		int[] px = { (int) (dim.width / 4), (int) (-dim.width / 2), (int) (-dim.width / 5), (int) (-dim.width / 2) };
//		int[] py = { 0, (int) (-dim.height / 2), 0, (int) (dim.height / 2) };
//		body = new Polygon(px, py, px.length);
		body = new Rectangle2D.Double(-dim.width - 12.5, -dim.height - dim.height, (dim.width*3) + 5, dim.height * 4);
		head = new Ellipse2D.Double(dim.width/2 - 2.5, (-dim.height * 2) - 3, (dim.width * 2) + 6, (dim.height *4) + 6);
		
		stripe1 = new Rectangle2D.Double(-dim.width - 5.5, -dim.height * 2, 1, dim.height*4);
		stripe2 = new Rectangle2D.Double(-dim.width + 5.5, -dim.height * 2, 1, dim.height*4);
		stripe3 = new Rectangle2D.Double(-dim.width + 15.5, -dim.height * 2, 1, dim.height*4);
		stripe4 = new Rectangle2D.Double(-dim.width + 25.5, -dim.height * 2, 1, dim.height*4);
		
		nose = new Ellipse2D.Double(dim.width +2.5, -dim.height + 8, dim.width - 17, dim.height -7);
		
		rightLeg = new Rectangle2D.Double(-dim.width - 5, dim.height + 10, dim.width - 18, dim.height + 14);
		rightFeet = new Rectangle2D.Double();
		
		InnerrightLeg = new Rectangle2D.Double(-dim.width + 3, dim.height + 10, dim.width - 18, dim.height + 4);
		
		leftLeg = new Rectangle2D.Double(0, dim.height + 10, dim.width/2 - 8, dim.height + 14);
		InnerleftLeg = new Rectangle2D.Double(dim.width - 18, dim.height + 10, dim.width/2 - 8, dim.height + 4);
		
		eyeRight = new Ellipse2D.Double(dim.width + 17.5, -dim.height - 4, dim.width - 15, dim.height- 5);
		eyeLeft = new Ellipse2D.Double(dim.width + 17.5, dim.height - 1, dim.width - 15, dim.height- 5);
		
		mouth = new Ellipse2D.Double(dim.width - 10.5, -dim.height + 3, dim.width/2, dim.height+5);
		InnerMouth = new Ellipse2D.Double(dim.width - 10.5, -dim.height + 6, dim.width/2 - 4, dim.height - 2);
		
		line1 = new Line2D.Double(dim.width + 4.5, -dim.height, -dim.width + 20, -dim.height*4);
		line2 = new Line2D.Double(dim.width + 4.5, -dim.height,  dim.width, -dim.height*4);
		line3 = new Line2D.Double(dim.width + 4.5, -dim.height,  dim.width - 10, -dim.height*4);
		
		line4 = new Line2D.Double(dim.width + 4.5, dim.height, -dim.width + 20, dim.height*4);
		line5 = new Line2D.Double(dim.width + 4.5, dim.height, dim.width, dim.height*4);
		line6 = new Line2D.Double(dim.width + 4.5, dim.height, dim.width - 10, dim.height*4);
		
		earRight = new Ellipse2D.Double(dim.width + 2.5, -dim.height - 16, dim.width - 4, dim.height - 4);
		earLeft = new Ellipse2D.Double(dim.width + 2.5, dim.height + 10, dim.width - 4, dim.height - 4);
		
		
	}
	

	@Override
	public void draw(Graphics2D g) {
		// transformation
		AffineTransform at = g.getTransform();
		g.translate(pos.x, pos.y);
		g.rotate(speed.heading());
		g.scale(size, size);
		if (speed.x < 0)
		g.scale(1, -1);

		
		if(state == SICK ) g.setColor(new Color(255,255,80));
		else g.setColor(new Color(255,255,80));
        g.fill(body);
		// body
        if(state == SICK ) g.setColor(new Color(255,255,80));
        else g.setColor(new Color(255,255,80));
        
		g.fill(head);
		
		
		g.setColor(Color.black);
		g.fill(stripe1);
		g.fill(stripe2);
		g.fill(stripe3);
		g.fill(stripe4);
		g.fill(nose);
		
		g.fill(rightLeg);		
		g.fill(InnerrightLeg);
		g.fill(leftLeg);		
		g.fill(InnerleftLeg);
		
		g.setColor(Color.RED);
		g.fill(InnerMouth);
		
		g.setColor(Color.WHITE);
		g.draw(InnerMouth);


		// eye		
		if (state == HUNGRY) {
			g.setColor(Color.WHITE);
			
			eyeLeft.setFrame(dim.width + 17.5, dim.height - 1, dim.width - 15, dim.height- 5);
			eyeRight.setFrame(dim.width + 17.5, -dim.height - 4, dim.width - 15, dim.height- 5);
			
		} else if (state == HALF_FULL) {
			g.setColor(Color.ORANGE);
			eyeLeft.setFrame(dim.width + 17.5, dim.height - 1, dim.width - 12, dim.height-2);
			eyeRight.setFrame(dim.width + 17.5, -dim.height - 4, dim.width - 12, dim.height-2);
		} else {
			g.setColor(Color.blue); 
			eyeLeft.setFrame(dim.width + 17.5, dim.height - 1, dim.width - 9, dim.height + 1);
			eyeRight.setFrame(dim.width + 17.5, -dim.height - 4, dim.width - 9, dim.height + 1);
		}
		
		g.fill(eyeLeft);
		g.fill(eyeRight);
		
		g.setColor(Color.BLACK);
		
		g.draw(line1);
		g.draw(line2);
		g.draw(line3);
		
		g.draw(line4);
		g.draw(line5);
		g.draw(line6);

		g.setTransform(at);
	}

	@Override
	protected void traceBestFood(ArrayList<SimulationObject> fList) {
		if (fList.size() > 0) {
			// find 1st target
			SimulationObject target = fList.get(0);
			float distToTarget = PVector.dist(pos, target.getPos());

			// find the closer one
			for (SimulationObject f : fList)
				if (PVector.dist(pos, f.getPos()) < distToTarget) {
					target = f;
					distToTarget = PVector.dist(pos, target.getPos());
				}

			// make animal follow this target
			this.approach(target);
		}
	}

	@Override
	protected void setOutline() {
		outline = new Area(head);
		outline.add(new Area(body));
	}

	@Override
	protected Shape getOutline() {
		AffineTransform at = new AffineTransform();
		at.translate(pos.x, pos.y);
		at.rotate(speed.heading());
		at.scale(size, size);
		return at.createTransformedShape(outline);
	}

	@Override
	protected boolean eatable(SimulationObject food) {
		return (food instanceof Food);
	}

	@Override
	public void checkCollision(LionPanel pnl) {
		// TODO Auto-generated method stub
		
	}

}
