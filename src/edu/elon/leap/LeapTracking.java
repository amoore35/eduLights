package edu.elon.leap;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.leapmotion.leap.*;

public class LeapTracking extends PApplet {
	
	float width = 300;
	float leftX = -150;
	float topY = 360;
	float height = 200;
	float rightX = 150;
	float bottomY = 160;
	String colors[][] = {{"red", "sub"}, {"yellow", "white"}, {"blue", "black"}, {"add", "reset"}};
	
	List<String> possColors = new ArrayList<>(Arrays.asList("red", "yellow", "blue", "green", "purple", "orange", "white", "black", "pink"));
	List<String> possOps = new ArrayList<>(Arrays.asList("sub", "add"));
	final String reset = "reset";
	
	double lastTime;
	String color = "";
	String prevColor = "";
	String colorToChange = "";
	String currLightColor = "white";
	private Controller controller = new Controller();
	
	private ArrayList<String> sequence = new ArrayList<>();
	
	Map<List<String>, String> addTable = new HashMap<List<String>, String>() {{
		put(Collections.unmodifiableList(Arrays.asList("red", "red")), "red");
		put(Collections.unmodifiableList(Arrays.asList("red", "yellow")), "orange");
		put(Collections.unmodifiableList(Arrays.asList("red", "blue")), "purple");
		put(Collections.unmodifiableList(Arrays.asList("red", "white")), "pink");
		put(Collections.unmodifiableList(Arrays.asList("pink", "black")), "red");
		put(Collections.unmodifiableList(Arrays.asList("yellow", "red")), "orange");
		put(Collections.unmodifiableList(Arrays.asList("yellow", "yellow")), "yellow");
		put(Collections.unmodifiableList(Arrays.asList("yellow", "blue")), "green");
		put(Collections.unmodifiableList(Arrays.asList("blue", "red")), "purple");
		put(Collections.unmodifiableList(Arrays.asList("blue", "yellow")), "green");
		put(Collections.unmodifiableList(Arrays.asList("blue", "blue")), "blue");
	}};
	
	Map<List<String>, String> subTable = new HashMap<List<String>, String>() {{
		put(Collections.unmodifiableList(Arrays.asList("red", "red")), "white");
		put(Collections.unmodifiableList(Arrays.asList("red", "yellow")), "red");
		put(Collections.unmodifiableList(Arrays.asList("red", "blue")), "red");
		put(Collections.unmodifiableList(Arrays.asList("red", "black")), "pink");
		put(Collections.unmodifiableList(Arrays.asList("pink", "white")), "red");
		put(Collections.unmodifiableList(Arrays.asList("yellow", "red")), "yellow");
		put(Collections.unmodifiableList(Arrays.asList("yellow", "yellow")), "white");
		put(Collections.unmodifiableList(Arrays.asList("yellow", "blue")), "yellow");
		put(Collections.unmodifiableList(Arrays.asList("blue", "red")), "blue");
		put(Collections.unmodifiableList(Arrays.asList("blue", "yellow")), "blue");
		put(Collections.unmodifiableList(Arrays.asList("blue", "blue")), "white");
		put(Collections.unmodifiableList(Arrays.asList("orange", "red")), "yellow");
		put(Collections.unmodifiableList(Arrays.asList("orange", "yellow")), "red");
		put(Collections.unmodifiableList(Arrays.asList("orange", "blue")), "orange");
		put(Collections.unmodifiableList(Arrays.asList("green", "red")), "green");
		put(Collections.unmodifiableList(Arrays.asList("green", "yellow")), "blue");
		put(Collections.unmodifiableList(Arrays.asList("green", "blue")), "yellow");
		put(Collections.unmodifiableList(Arrays.asList("purple", "red")), "blue");
		put(Collections.unmodifiableList(Arrays.asList("purple", "yellow")), "purple");
		put(Collections.unmodifiableList(Arrays.asList("purple", "blue")), "red");
		
	}};
	
	Map<String, Map<List<String>, String>> values = new HashMap<String, Map<List<String>, String>>() {{
		put("add", addTable);
		put("sub", subTable);
	}};
	
	
	public void setup() {
		size(250, 400);
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
			lastTime = System.currentTimeMillis();
			if (!color.equals("")) {
				prevColor = color;
			}
			color = "";
		}
		
		if (x >= leftX && x <= rightX && y <= topY && y >= bottomY) {
			int colorX = Math.abs((int) ((x - leftX)/(width/4)));
			int colorY = Math.abs((int) ((topY - y)/(height/2)));
			String newColor = colors[colorX][colorY];
			if (color != newColor) {
				prevColor = color;
				color = newColor;
				lastTime = System.currentTimeMillis();
			}
			if (System.currentTimeMillis() - lastTime > 800) {
				colorToChange = color;

				addToSequence(colorToChange);
			}
		} else if (y > 450) {
			System.out.println("performing..");
			performSequence();
		}
		
		
		
		text("LIGHT COLOR: " + currLightColor, 50, 200);
		text("Color: " + color, 50, 150);
		text("Color to change: " + colorToChange, 50, 250);
		text("Elapsed time on one color: " + (System.currentTimeMillis() - lastTime), 50, 300);
		text("Sequence " + sequence, 50, 350);
		
		
	}
	
	private void performSequence() {
		
		if (sequence.size() == 1) {
			//Reset
			if (sequence.get(0).equals(reset)){
				currLightColor = "white";
				sequence.clear();
			} else {
				//Set to current color
				currLightColor = sequence.get(0);
			}
		} else if (sequence.size() == 2) {
			currLightColor = sequence.get(0);
		} else if (sequence.size() == 3) {
			String color1 = sequence.get(0);
			String op = sequence.get(1);
			String color2 = sequence.get(2);
			Map<List<String>, String> table = values.get(op);
			String newColor = table.get(Arrays.asList(color1, color2));
			if (newColor != null) {
				currLightColor = newColor;
			}
			sequence.clear();
			sequence.add(currLightColor);
			
			
		}
	}



	private void addToSequence(String color) {
		if (sequence.isEmpty() && possOps.contains(color)) {
			//do nothing because sequence can't start with an operation
			return;
		} else if (color.equals(reset)){
			sequence.clear();
			sequence.add(color);
		} else if (sequence.isEmpty() && !possOps.contains(color)) {
			sequence.add(color);
		} else if (sequence.get(sequence.size() - 1).equals(color)) {
			return;
		}
		else if ((possColors.contains(sequence.get(sequence.size() - 1)) && possColors.contains(color))
				|| (possOps.contains(sequence.get(sequence.size() - 1)) && possOps.contains(color))) {
			System.out.println("REPLACING..");
			sequence.set(sequence.size() - 1, color);
		} else if (sequence.size() == 1 && sequence.get(0) == reset ) {
			if (possOps.contains(color)) return;
			sequence.set(0, color);
		}
		
		else if (sequence.size() < 3){
			System.out.println("NOT REPLACING...");
			sequence.add(color);
		}
	}
	

}
