package ecosys.simulation;

import main.LionPanel;

public interface Mover {

	
	void move();
	void approach(SimulationObject obj);
	void checkCollision(LionPanel pnl);
	
}
