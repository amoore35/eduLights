package edu.elon.lights.apps;

import edu.elon.kinect.PositionTracking;

public class BodyMovement extends PositionTracking {
	
	private float lastPosition;
	private static final int THRESHOLD = 150;
	
	public BodyMovement() {
		super();
		lastPosition = pos.x;
	}

	@Override
	public void draw() {
		context.update();
	

		for (int user : users) {
			context.getCoM(user, pos);
			
			if (Math.abs(pos.x - lastPosition) > THRESHOLD) {
				lastPosition = pos.x;
				controller.turnOnAllRandom();
				System.out.println(lastPosition);
			}
		}
		

	}
}
