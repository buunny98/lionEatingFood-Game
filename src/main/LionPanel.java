package main;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.TimerTask;

import javax.swing.JPanel;
import javax.swing.Timer;

import ecosys.simulation.Missile;
import ecosys.simulation.Hunter;
import ecosys.simulation.SimulationObject;
import ecosys.simulation.Predator;
import ecosys.simulation.Prey;
import util.Util;

public class LionPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private ArrayList<SimulationObject> objList;
	private Hunter p;

	private boolean state = false;

	private Rectangle2D.Double beach;
	private Rectangle2D.Double forest1;

	private Ellipse2D.Double sun;
	private Ellipse2D.Double pond;

	private Ellipse2D.Double stone;

	private Ellipse2D.Double fishHead1;
	private Ellipse2D.Double fishHead2;
	private Ellipse2D.Double fishTail1;
	private Ellipse2D.Double fishTail2;

	private Ellipse2D.Double fishEye1;
	private Ellipse2D.Double fishEye2;
	private Line2D.Double fishSmile1;
	private Line2D.Double fishSmile2;

	public  Timer t;

	private int MAX_FOOD = 18;
	public static int MAX_P = 12;
	public static  int MAX_PRE = 6;

	private String status = "status...";
	public static boolean showInfo = true;



	public final static Dimension PAN_SIZE = new Dimension(1200,800);

	public boolean up, down, left , right, fire;

	public LionPanel() {
		super();
		this.setPreferredSize(PAN_SIZE);

		this.objList = new ArrayList<>();

		for (int i = 0; i < MAX_P; i++) {
			this.addPrey();
		}

		for (int i = 0; i < MAX_PRE; i++) {
			this.addPredator();
		}


		// and new foods
		for (int i = 0; i < MAX_FOOD; i++)
			objList.add(Util.randomFood(this));

		p = new Hunter(PAN_SIZE.width/2 - 500, PAN_SIZE.height/2, 1.5f, objList, this);


		t = new Timer(33, this);
		t.start();



		addKeyListener(new MyKeyAdapter());
		setFocusable(true);

		sun = new Ellipse2D.Double();
		pond = new Ellipse2D.Double();
		stone = new Ellipse2D.Double();
		beach = new Rectangle2D.Double();
		forest1 = new Rectangle2D.Double();

		fishHead1 = new Ellipse2D.Double();
		fishHead2 = new Ellipse2D.Double();
		fishTail1 = new Ellipse2D.Double();
		fishTail2 = new Ellipse2D.Double();
		fishEye1 = new Ellipse2D.Double();
		fishEye2 = new Ellipse2D.Double();

		fishSmile1 = new Line2D.Double();
		fishSmile2 = new Line2D.Double();

	}


	public void BeachAndSun() {
		sun.setFrame(5, 5, 100, 100);
		pond.setFrame(170, 625, 900, 100);
		//pond.setFrame(forest_X, forest_Y, 400, 90);

		beach.setRect(0, 615, 1070 + 29 + 100, 160);
		stone.setFrame(2, 625, 160, 125);


		fishHead1.setFrame(130 + 170, 380 + 270, 30, 25);//Fish 1 head		
		fishHead2.setFrame(330 + 350, 380 + 290, 30, 25);//Fish 2 head

		fishTail2.setFrame(345 + 350, 385 + 290, 20, 15);//Fish 1 Tail
		fishTail1.setFrame(145 + 170, 385 + 270, 20, 15);//Fish 2 Tail

		fishEye1.setFrame(135 + 170, 385 + 270, 5, 5); //Fish 1 Eyes

		fishEye2.setFrame(335 + 350, 385 + 290, 5, 5); //Fish 2 Eyes

		fishSmile1.setLine(132 + 170, 395 + 270, 142 + 170, 393 + 270); //The smile of the fish 1, fish is confused she does not know where all his friends are going. (Eaten by the lion)
		fishSmile2.setLine(332 + 350, 395 + 290, 342 + 350, 393 + 290); //The smile of the fish 1, fish is confused she does not know where all his friends are going. (Eaten by the lion)


	}

	public void forest() {
		forest1.setRect(50, 50, PAN_SIZE.width - 120, PAN_SIZE.height - 230);

	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(new Color(0, 200, 0));
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


		BeachAndSun();
		forest();

		g2.setColor(new Color(85,107,47));
		g2.fill(forest1);

		g2.setColor(new Color(255, 147, 61));
		g2.fill(sun); //The sun in the environment 

		g2.setColor(Color.YELLOW);
		g2.draw(sun);//The outline of the sun in the environment

		g2.setColor(Color.BLACK);
		g2.draw(beach); //The outline of the beach

		g2.setColor(new Color(238,214,175));//The beach 
		g2.fill(beach);

		g2.setColor(new Color(28,163,236));//The pond
		g2.fill(pond);

		//The fishes in the pond.
		g2.setColor(Color.BLUE);
		g2.fill(fishHead1);//Fish 1 head		
		g2.fill(fishHead2);//Fish 2 head

		g2.setColor(new Color (0, 0, 180));
		g2.fill(fishTail1);//Fish 2 Tail
		g2.fill(fishTail2);//Fish 1 Tail



		g2.setColor(Color.black); 
		g2.fill(fishEye1); //Fish 1 Eyes

		g2.fill(fishEye2); //Fish 2 Eyes

		g2.draw(fishSmile1); //The smile of the fish 1, fish is confused she does not know where all his friends are going. (Eaten by the lion)
		g2.draw(fishSmile2); //The smile of the fish 1, fish is confused she does not know where all his friends are going. (Eaten by the lion)


		g2.setColor(new Color (53,	66,	74));
		g2.fill(stone);

		// draw objects
		for (SimulationObject obj : objList) {
			obj.draw(g2);
			if(showInfo) obj.drawInfo(g2);
		}

		if(state == true) {
			p.draw(g2);	
		}
        if(p.health <= 0) {
        	p.deadDraw(g2);
        	p.sUp();
        	p.sDown();
        }
		drawStatusBar(g2);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

				for(int i=0; i<objList.size();i++) {
					for(int i1=0; i1<objList.size();i1++) {
						if(objList.get(i) instanceof Hunter && objList.get(i1) instanceof Predator) {
							Hunter PP = (Hunter) objList.get(i);
							Predator PP1 = (Predator) objList.get(i1);
							
							if(PP.isColliding(PP1)) {
								//objList.remove(PP);
								System.out.println("YES");
							}
						}
					}
				}

//
//		if(MAX_PRE <=  1) {
//			float  x = Util.random(70, PAN_SIZE.width - 125);
//			float y =  Util.random(70, PAN_SIZE.height - 240);
//			float size = Util.random(0.6f,1.2f);
//			System.out.println(t);
//			for (int i = 0; i < MAX_PRE; i++) objList.add(new Predator(x, y , size));			
//		}

		// update every object in the simulation
		for (int i = 0; i < objList.size(); i++) {
			objList.get(i).update(objList, this);
			if(objList.get(i) instanceof Predator) ((Predator) objList.get(i)).update123(objList);
		}

		// replace eaten food
		if (Util.countFood(objList) < MAX_FOOD) {
			objList.add(Util.randomFood(this));
		}


		p.update();

		if(fire) p.fire();





		for( int i = 0; i <objList.size(); i++) {

			if(objList.get(i) instanceof Prey && MAX_P <= 6) {
				//System.out.println(MAX_P);
				state = true;
			}

			if(MAX_PRE <= 3) {
				//System.out.println(MAX_P);
				state = false;
			}
		}

		repaint();
	}

	void destroyLi(Prey b) {
		objList.remove(b);
	}

	private class MyKeyAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			//			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			//				if (t.isRunning())
			//					t.stop();
			//				else
			//					t.start();
			//			}
			//
			//			if (e.getKeyCode() == KeyEvent.VK_UP)
			//				t.setDelay(t.getDelay() / 2);
			//			if (e.getKeyCode() == KeyEvent.VK_DOWN)
			//				t.setDelay(t.getDelay() * 2);

			//			if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			//				right = true;
			//			}
			//			if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			//				left = true;
			//			}
			//			if(e.getKeyCode() == KeyEvent.VK_UP) {
			//				up = true;
			//			}
			//			if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			//				down = true;
			//			}
			if(e.getKeyCode() == KeyEvent.VK_SPACE) {
				fire = true;

			}

			if (e.getKeyCode() == KeyEvent.VK_D) {
				//				System.out.println(showInfo);
				if (showInfo)
					showInfo = false;
				else
					showInfo = true;
			}
		}
		@Override
		public void keyReleased(KeyEvent e) {
			//			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			//				if (t.isRunning())
			//					t.stop();
			//				else
			//					t.start();
			//			}
			//
			//			if (e.getKeyCode() == KeyEvent.VK_UP)
			//				t.setDelay(t.getDelay() / 2);
			//			if (e.getKeyCode() == KeyEvent.VK_DOWN)
			//				t.setDelay(t.getDelay() * 2);

			if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
				right = false;
			}
			if(e.getKeyCode() == KeyEvent.VK_LEFT) {
				left = false;
			}
			if(e.getKeyCode() == KeyEvent.VK_UP) {
				up = false;
			}
			if(e.getKeyCode() == KeyEvent.VK_DOWN) {
				down = false;
			}
			if(e.getKeyCode() == KeyEvent.VK_SPACE) {
				fire = false;
			}


		}
	}

	private void drawStatusBar(Graphics2D g) {
		Font f = new Font("Arial", Font.BOLD, 12);
		g.setFont(f);
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, getSize().height-24, 
				getSize().width, 24);
		g.setColor(Color.BLACK);
		g.drawString(status, 12, getSize().height-8);
	}

	public void setStatus(String st) {
		this.status = st;
	}

	public void addPrey() {
		float  x = Util.random(70, PAN_SIZE.width - 130);
		float y =  Util.random(70, PAN_SIZE.height - 240);
		float size = Util.random(0.4f,0.8f);
		this.objList.add(new Prey(x, y, size));
	}

	public void addHunter() {

		this.objList.add(new Hunter(PAN_SIZE.width/2, PAN_SIZE.height/2, 1f, objList, this));
	}

	public void addPredator() {
		float  x = Util.random(70, PAN_SIZE.width - 125);
		float y =  Util.random(70, PAN_SIZE.height - 240);
		float size = Util.random(0.6f,1.2f);
		this.objList.add(new Predator(x, y, size));
	}

	public void reSpawn() {
		for (int i = 0; i < 6; i++) {
			float  x = Util.random(70, PAN_SIZE.width - 125);
			float y =  Util.random(70, PAN_SIZE.height - 240);
			float size = Util.random(0.6f,1.2f);
			objList.add(new Predator( x, y, size ));

		}
	}

}
