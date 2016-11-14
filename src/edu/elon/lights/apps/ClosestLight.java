package edu.elon.lights.apps;

import edu.elon.kinect.PositionTracking;

public class ClosestLight extends PositionTracking {

	private static final int MID_X = 0;
	
	public ClosestLight() {
		super();
	}
	
	public void draw() {
		context.update();

		for (int user : users) {
			context.getCoM(user, pos);
			if (pos.x > MID_X + 200 && current == false) {
				System.out.println("Greater than midpoint");
				
				controller.turnOnRandomLeft();
				controller.turnOffRightLight();
				current = true;
			}
			if (pos.x < MID_X - 200 && current == true) {
				controller.turnOffLeftLight();
				controller.turnOnRandomRight();
				current = false;
			}
		}
	}
	
}
