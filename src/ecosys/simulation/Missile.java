package ecosys.simulation;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import main.LionPanel;
import processing.core.PVector;

public class Missile extends SimulationObject implements Mover {

	private PVector speed;
	private Ellipse2D missile;

	public Missile(float x, float y, float speedx, float speedy) {
		super(x, y, 10, 10, 1f);
		speed = new PVector(speedx, speedy);
	}

	@Override
	public void draw(Graphics2D g) {
		AffineTransform at = g.getTransform();
		g.translate(pos.x, pos.y);
		g.scale(size, size);
		g.rotate(speed.heading());
		g.setColor(Color.CYAN);
		g.fill(missile);
		g.setTransform(at);
	}

	@Override
	public void drawInfo(Graphics2D g2) {
		// nothing to do here
	}



	@Override
	public void update(ArrayList<SimulationObject> animList, LionPanel panel) {


		//Rectangle2D env = new Rectangle2D.Double(0, 0, panel.PAN_SIZE.width, panel.PAN_SIZE.height);

		move();

		for (SimulationObject obj : animList)
			if (obj instanceof Predator && isColliding(obj)) {

				// someone got shot
				animList.remove(obj); 

				LionPanel.MAX_PRE = LionPanel.MAX_PRE - 1;
				System.out.println(LionPanel.MAX_PRE);
				Hunter.missileList.remove(this);

				break;
			}
		traceBest(animList);           


	}

	@Override
	protected void setShapeAttributes() {
		missile = new Ellipse2D.Double(-dim.width / 2, -dim.height / 2, dim.width, dim.height);
	}

	@Override
	protected void setOutline() {
		outline = new Area(missile);
	}

	@Override
	protected Shape getOutline() {
		AffineTransform at = new AffineTransform();
		at.translate(pos.x, pos.y);
		at.scale(size, size);
		at.rotate(speed.heading());
		return at.createTransformedShape(outline);
	}

	@Override
	public void move() {
		pos.add(speed);
	}

	@Override
	public void approach(SimulationObject obj) {

		float coef = .1f; // coefficient of acceleration relative to maxSpeed
		PVector direction = PVector.sub(obj.getPos(), pos).normalize();
		PVector accel = PVector.mult(direction, speed.mag() * coef);
		speed.add(accel);

	}

	@Override
	public void checkCollision(LionPanel pnl) {
		// nothing to do here
	}

	protected float getAttraction(SimulationObject target) {
		//return (target.getSize()*100)/(PVector.dist(pos, target.getPos())/this.speedMag * engLossRatio);
		return (target.getSize()) *20/(PVector.dist(pos, target.getPos()) * 20/this.speed.mag());
	}



	protected void traceBest(ArrayList<SimulationObject> objList) {	

		for (SimulationObject f: objList) { 

			if (f instanceof Predator && objList.size()>0) {	

				SimulationObject target = objList.get(0);
				float targetAttraction = this.getAttraction(target);

				// find the closer one
				//for //(SimulationObject f:fList) 
				if (this.getAttraction(f) > targetAttraction) {
					target = f;
					targetAttraction = this.getAttraction(target);
				}


				// make animal follow this target
				this.approach(target);
			}	
		}
	}

}