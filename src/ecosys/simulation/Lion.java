package ecosys.simulation;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import main.LionPanel;
import processing.core.PVector;
import util.Util;

public abstract class Lion extends SimulationObject implements Mover{

	protected PVector speed; // speed
	protected float speedMag; // speed limit
	protected float energy; // energy
	protected final float FULL_ENERGY = 1000;
	protected float engGainRatio = 100; // Energy gained per food size unit
	protected float engLossRatio = FULL_ENERGY / (30 * 15); // Energy loss per frame
	protected float sizeGrowRatio = 0.0001f; // size growth ratio per extra energy unit
	
	protected float gainWeightEnergy = 500;

	protected Color color; // featured color
	//protected Arc2D.Double head; // the original body
	protected Ellipse2D.Double eye; // the eye
	//protected Polygon body; // tail
	
	
	protected Rectangle2D.Double body;
	protected Ellipse2D.Double head;
	
	protected Ellipse2D.Double earRight;
	protected Ellipse2D.Double earLeft;
	
	protected Ellipse2D.Double eyeRight;
	protected Ellipse2D.Double eyeLeft;
	protected Rectangle2D.Double stripe1;
	protected Rectangle2D.Double stripe2;
	protected Rectangle2D.Double stripe3;
	protected Rectangle2D.Double stripe4;
	
	protected Rectangle2D.Double rightLeg;
	protected Rectangle2D.Double rightFeet;
	
	protected Rectangle2D.Double InnerrightLeg;
	protected Rectangle2D.Double InnerrightFeet;
	
	protected Rectangle2D.Double InnerleftLeg;
	protected Rectangle2D.Double InnerleftFeet;
	
	protected Rectangle2D.Double leftLeg;
	protected Rectangle2D.Double leftFeet;
	
	protected Ellipse2D.Double mouth;
	protected Ellipse2D.Double InnerMouth;
	
	protected Ellipse2D.Double nose;
	
	protected Line2D.Double line1;
	protected Line2D.Double line2;
	protected Line2D.Double line3;
	protected Line2D.Double line4;
	protected Line2D.Double line5;
	protected Line2D.Double line6;
	

	

	// FSM states
	protected int state;
	protected final int HUNGRY = 0;
	protected final int HALF_FULL = 1;
	protected final int FULL = 2;
	protected final int OVER_FULL = 3;
	protected final int SICK = 4;
	protected final int DEATH = -1;
	
	public Lion(float x, float y, int w, int h, float size) {
		super(x, y, w, h, size);
		speedMag = 5f;
//		speed = Util.randomPVector(speedMag);
		speed = Util.randomPVector(speedMag);
		state = HUNGRY;
		
	}

	public void move() {
		speed.normalize().mult(speedMag);

		// apply speed to position
		pos.add(speed);

		// lose energy
		energy -= engLossRatio;
	}

	public void approach(SimulationObject target) {
		float coef = .3f; // coefficient of acceleration relative to maxSpeed
		PVector direction = PVector.sub(target.getPos(), pos).normalize();
		PVector accel = PVector.mult(direction, speedMag * coef);
		speed.add(accel);
	}

	@Override
	public void update(ArrayList<SimulationObject> objList, LionPanel panel) {
		ArrayList<SimulationObject> fList = filterTargetList(objList);
		traceBestFood(fList);
		checkCollision();
		move();
      


		for (int i = 0; i < fList.size(); i++)
			if (isColliding(fList.get(i))) {
				float foodSize = fList.get(i).getSize();
				energy += (foodSize + 1) * engGainRatio;
				if (energy > FULL_ENERGY) {
					state = OVER_FULL;
					//speedMag = (float) (speedMag + 0.5);
				}
				
				else if (energy == FULL_ENERGY) {
					state = FULL;
					//speedMag = (float) (speedMag - 0.5);
					speedMag -= 0.5;
				}
				else if (energy > FULL_ENERGY / 2) {
					state = HALF_FULL;
					//speedMag = (float) (speedMag - 0.5);
					speedMag -= 0.5;
				}
				else if(energy > FULL_ENERGY/3) {				
					state = HUNGRY;
					//speedMag = (float) (speedMag - 0.5);
					speedMag -= 0.5;
				}
				else if(energy > -0.2*FULL_ENERGY) {
					state = SICK;
					//speedMag = (float) (speedMag - 0.5);
					speedMag -= 0.5;
				}	
				else {
					state = DEATH;
					speedMag = 0;
				}
				
				if(energy > gainWeightEnergy) {
					size = (float) 1.2;
				}

				String st = String.format("%s gains energy by %.2f units to %.2f", animalType(), foodSize * 100,
						energy);
				//System.out.println(st);
				if (state == OVER_FULL) {
					float extra = energy - FULL_ENERGY;
					energy = FULL_ENERGY;
					size += extra * sizeGrowRatio * size;
					st = String.format("%s grows by %.1f%% to %.2f%n", animalType(), energy * .01, size);
					panel.setStatus(st);
					//System.out.println(st);
				}
				
				if(state == DEATH) {
					panel.setStatus(this.animalType() + "died....");
					objList.remove(this);
					return;
				}
				objList.remove(fList.get(i));
				
			}
	}

	private String animalType() {
		String type = "unknown animal";
		if (this instanceof Predator)
			type = "Predator";
		else if (this instanceof Prey)
			type = "Prey";
		else if(this instanceof Hunter)
			type = "Hunter";
		return type;
	}

	protected ArrayList<SimulationObject> filterTargetList(ArrayList<SimulationObject> fList) {
		ArrayList<SimulationObject> list = new ArrayList<>();
		for (SimulationObject f : fList)
			if (eatable(f))
				list.add(f);
		return list;
	}
	
	public void drawInfo(Graphics2D g) {
		AffineTransform at = g.getTransform();
		g.translate(pos.x, pos.y);
		
		String st1 = "Size     : " + String.format("%.2f", size);
		String st2 = "Speed  : " + String.format("%.2f", speed.mag());
		String st3 = "Energy : " + String.format("%.2f", energy);
		
		Font f = new Font("Courier", Font.PLAIN, 12);
		FontMetrics metrics = g.getFontMetrics(f);
		
		float textWidth = metrics.stringWidth(st3);
		float textHeight = metrics.getHeight();
		float margin = 12, spacing = 6;
		
		g.setColor(new Color(255,255,255,60));
		g.fillRect((int)(-textWidth/2-margin), 
				(int)(-dim.height*size*.75f - textHeight*5f - spacing*4f - margin*2f), 
				(int)(textWidth + margin*1f), 
				(int)(textHeight*5f + spacing*4f + margin*1f));
		
		g.setColor(Color.blue.darker());
		
		g.drawString(this.animalType(), -metrics.stringWidth(this.animalType())/2,  -dim.height*size*.75f - margin - (textHeight + spacing)*4f);
		g.setColor(Color.black);
		g.drawString(st1, -textWidth/2, -dim.height*size*.75f - margin - (textHeight + spacing)*3f);
		g.drawString(st2, -textWidth/2, -dim.height*size*.75f - margin - (textHeight + spacing)*2f);
		if(state == SICK) g.setColor(Color.RED);
		g.drawString(st3, -textWidth/2, (-dim.height*size*.75f - margin) - 20);
		
		g.setTransform(at);
	}

	public void checkCollision() {
		float coef = 50f;
		
		Line2D.Double TOP_LINE = new Line2D.Double(50, 50, LionPanel.PAN_SIZE.width - 120, 50);
		Line2D.Double BTM_LINE = new Line2D.Double(50, LionPanel.PAN_SIZE.height - 230, LionPanel.PAN_SIZE.width  - 120, LionPanel.PAN_SIZE.height - 230);
		Line2D.Double LFT_LINE = new Line2D.Double(50, 50, 50, LionPanel.PAN_SIZE.height - 230);
		Line2D.Double RGT_LINE = new Line2D.Double(LionPanel.PAN_SIZE.width - 120, 50, LionPanel.PAN_SIZE.width - 230, LionPanel.PAN_SIZE.height);
		
		double top_dist = TOP_LINE.ptLineDist(pos.x, pos.y) - getBoundingBox().getHeight()/2;
		double btm_dist = BTM_LINE.ptLineDist(pos.x, pos.y) - getBoundingBox().getHeight()/2;
		double lft_dist = LFT_LINE.ptLineDist(pos.x, pos.y) - getBoundingBox().getWidth()/2;
		double rgt_dist = RGT_LINE.ptLineDist(pos.x, pos.y) - getBoundingBox().getWidth()/2;
		
		PVector top_f = new PVector(0,1).mult((float)(coef/Math.pow(top_dist, 2f)));
		PVector btm_f = new PVector(0,-1).mult((float)(coef/Math.pow(btm_dist, 2f)));
		PVector lft_f = new PVector(1,0).mult((float)(coef/Math.pow(lft_dist, 2f)));
		PVector rgt_f = new PVector(-1,0).mult((float)(coef/Math.pow(rgt_dist, 2f)));
				
		speed.add(top_f).add(btm_f).add(lft_f).add(rgt_f);
	}
	
	protected abstract void traceBestFood(ArrayList<SimulationObject> fList);

	protected abstract boolean eatable(SimulationObject food);
}
