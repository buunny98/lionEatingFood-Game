package ecosys.simulation;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Formatter;

import javax.swing.Timer;

import main.LionPanel;
import processing.core.PVector;
import util.Util;

public class Hunter extends Prey {

	private Ellipse2D circle;

	private ArrayList<SimulationObject> animList;
	private LionPanel pane;
	public float health = 50;
	static ArrayList<Missile> missileList = new ArrayList<Missile>();






	public Hunter(float x, float y, float size, ArrayList<SimulationObject> animList, LionPanel pane) {
		super(x, y, size);
		this.animList = animList;
		this.pane = pane;
		speed.set(0, 4); 


	}

	@Override
	public void setShapeAttributes() {

		super.setShapeAttributes();

	}
	
	public void deadDraw(Graphics2D g) {
		AffineTransform at = g.getTransform();
		g.translate(pos.x, pos.y);
		g.rotate(speed.heading());
		g.scale(size, size);
		if (speed.x < 0)
			g.scale(1, -1);
		g.setColor(Color.black);
		g.fill(body);
		g.setColor(Color.black);
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

		g.setColor(Color.black);
		g.fill(InnerMouth);

		g.setColor(Color.black);
		g.draw(InnerMouth);


		g.setColor(Color.BLACK);

		g.draw(line1);
		g.draw(line2);
		g.draw(line3);

		g.draw(line4);
		g.draw(line5);
		g.draw(line6);

		eyeLeft.setFrame(dim.width + 17.5, dim.height - 1, dim.width - 15, dim.height- 5);
		eyeRight.setFrame(dim.width + 17.5, -dim.height - 4, dim.width - 15, dim.height- 5);

		g.setColor(Color.black);
		g.fill(eyeLeft);
		g.fill(eyeRight);



		g.setTransform(at);

	}

	@Override
	public void draw(Graphics2D g) {

		super.draw(g);
		
		AffineTransform at = g.getTransform();
		g.translate(pos.x, pos.y);
		g.rotate(speed.heading());
		g.scale(size, size);
		if (speed.x < 0)
			g.scale(1, -1);
		g.setColor(Color.red);
		g.fill(body);
		g.setColor(Color.orange);
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


		g.setColor(Color.BLACK);

		g.draw(line1);
		g.draw(line2);
		g.draw(line3);

		g.draw(line4);
		g.draw(line5);
		g.draw(line6);

		eyeLeft.setFrame(dim.width + 17.5, dim.height - 1, dim.width - 15, dim.height- 5);
		eyeRight.setFrame(dim.width + 17.5, -dim.height - 4, dim.width - 15, dim.height- 5);

		g.setColor(Color.RED);
		g.fill(eyeLeft);
		g.fill(eyeRight);



		g.setTransform(at);

		if(LionPanel.showInfo == true) {
			drawInfo1(g);
		}

		for(Missile m: missileList) m.draw(g);

	}
	@Override
	public void checkCollision(LionPanel pnl) {


	}


	public void drawInfo1(Graphics2D g) {
		AffineTransform at = g.getTransform();
		g.translate(pos.x, pos.y);

		String st1 = "Size     : " + String.format("%.2f", size);
		String st2 = "Speed  : " + String.format("%.2f", speed.mag());
		String st3 = "Health  : " + String.format("%.0f", health);


		Font f = new Font("Courier", Font.PLAIN, 12);
		FontMetrics metrics = g.getFontMetrics(f);

		float textWidth = metrics.stringWidth(st2);
		float textHeight = metrics.getHeight();
		float margin = 8, spacing = 6;

		g.setColor(new Color(255,255,255));
		g.fillRect((int)(-textWidth/2-margin), 
				(int)(-dim.height*size*.75f - textHeight*5f - spacing*4f - margin*2f - 60), 
				(int)(textWidth + margin*1f), 
				(int)(textHeight*5f + spacing*4f + margin*1f));

		g.setColor(Color.blue.darker());

		g.drawString("Hunter", -20, -180);
		g.setColor(Color.black);
		g.drawString(st1, -textWidth/2, -dim.height*size*.75f - margin - (textHeight + spacing)*3f - 76);
		g.drawString(st3, -textWidth/2, -dim.height*size*.75f - margin - (textHeight + spacing)*2f - 71);
		g.drawString(st2, -textWidth/2, (-dim.height*size*.75f - margin)-20 - 66);

		g.setTransform(at);
	}
	
	public void sUp() {
		speed.set(speed.x, 0);
	}

	public void sDown() {
		speed.set(speed.x, 0);
	}


	public void up() {
		speed.set(speed.x, -6);
	}

	public void down() {
		speed.set(speed.x, 6);
	}
	@Override
	public void move() {

		pos.add(speed);
	}

	public void update() {
		//		
		if(pos.y < 125) {
			down();
		}

		if(pos.y > 530) {
			up();
		}

		move(); // move the player
		for(int i=0; i<missileList.size();i++) {

			missileList.get(i).update(animList, pane);			

		}


		for (SimulationObject obj : animList)
			if (obj instanceof Predator && isColliding(obj)) {
//
//				System.out.println("YESSSSS");

				health = (float) ((health - 1) + 0.9);
				break;
			} 
	}
	public void fire() {
		PVector mSpeed = PVector.fromAngle(speed.heading()).mult(5);

		missileList.add(new Missile(pos.x, pos.y, mSpeed.x, mSpeed.y));

	}

}