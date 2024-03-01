package main;

import javax.swing.JFrame;

public class LionApp extends JFrame {

	private static final long serialVersionUID = 6457792220456140992L;

	public LionApp(String title) {
		super(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// instantiating our BallPanel
		LionPanel panel = new LionPanel();
		
		// adding it to the current frame
		this.add(panel);
		this.pack();
		
		// displaying the frame
		this.setVisible(true);
	}

	public static void main(String[] args) {
		new LionApp("My Lion Game");
	}

}
