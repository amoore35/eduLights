package edu.elon.leap;

import processing.core.PApplet;
import com.leapmotion.leap.*;

public class LeapTracking extends PApplet {
	
	float redYellow = -70f;
	float yellowBlue = -5f;
	float blueYellow = 5f;
	float blueAdd = 80f;
	float mid = 350f;
	
	double elapsedTime;
	String color = "";
	String prevColor = "";
	boolean changed = false;
	String colorToChange = "";
	
	private Controller controller = new Controller();
	
	public void setup() {
		size(200, 250);
	}
	
	public void draw() {
		background(0);
		Frame frame = controller.frame();
		text(frame.hands().count() + " Hands", 50, 50);
		text(frame.fingers().count() + " Fingers", 50, 100);
		Finger pointable = frame.fingers().frontmost();
		float x = 0f;
		float y = pointable.stabilizedTipPosition().getY();
		if (pointable.isExtended()) {
			System.out.println(pointable.stabilizedTipPosition());
			x = pointable.stabilizedTipPosition().getX();
		} else {
			changed = true;
			elapsedTime = System.currentTimeMillis();
			if (!color.equals("")) {
				prevColor = color;
			}
			color = "";
		}
	
		
		if (x < redYellow) {
			if (y < mid) {
				if (color != "sub") {
					prevColor = color;
					changed = true;
					color = "sub";
					elapsedTime = System.currentTimeMillis();
				} else {
					changed = false;
				}
			} else {
				if (color != "red") {
					prevColor = color;
					changed = true;
					color = "red";
					elapsedTime = System.currentTimeMillis();
				} else {
					changed = false;
				}
			}
		} else if (x < yellowBlue) {
			if (y < mid) {
				if (color != "white") {
					prevColor = color;
					changed = true;
					color = "white";
					elapsedTime = System.currentTimeMillis();
				} else {
					changed = false;
				} 
			} else {
				if (color != "yellow") {
					prevColor = color;
					changed = true;
					color = "yellow";
					elapsedTime = System.currentTimeMillis();
				} else {
					changed = false;
				}
			}
		} else if (x > blueAdd) {
			if (y < mid) {
				if (color != "reset") {
					prevColor = color;
					changed = true;
					color = "reset";
					elapsedTime = System.currentTimeMillis();
				} else {
					changed = false;
				}
			} else {
				if (color != "add") {
					prevColor = color;
					changed = true;
					color = "add";
					elapsedTime = System.currentTimeMillis();
				} else {
					changed = false;
				}
			}
		} else if (x > blueYellow) {
			if (y < mid) {
				if (color != "black") {
					prevColor = color;
					changed = true;
					color = "black";
					elapsedTime = System.currentTimeMillis();
				} else {
					changed = false;
				}
			} else {
				if (color != "blue") {
					prevColor = color;
					changed = true;
					color = "blue";
					elapsedTime = System.currentTimeMillis();
				} else {
					changed = false;
				}
			}
		}
		System.out.println("millis: " + System.currentTimeMillis());
		System.out.println("elapsed Time: " + elapsedTime);
		
		if (System.currentTimeMillis() - elapsedTime > 1500) {
			colorToChange = color;
		}
		
	
		text("Previous Color:" + prevColor, 50, 200);
		text("Color: " + color, 50, 150);
		System.out.println("Color to change..." + colorToChange);
//		if (changed) {
//			prevColor = color;
//		}
//		changed = false;
		
	}

}
