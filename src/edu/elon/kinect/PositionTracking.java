package edu.elon.kinect;

import java.util.ArrayList;

import com.philips.lighting.model.PHLight;

import SimpleOpenNI.*;
import controlP5.*;
import g4p_controls.G4P;
import g4p_controls.GButton;
import g4p_controls.GCScheme;
import g4p_controls.GEvent;
import g4p_controls.GLabel;
import processing.core.*;
import java.awt.*;

public class PositionTracking extends PApplet{

	private SimpleOpenNI context;
	private ControlP5 cp5;
	private ArrayList<Integer> users = new ArrayList<>();
	private PVector pos = new PVector();
	private static final int MID_X = 0;
	private KinectLightController controller;
	boolean current = false;
	int colorWhite = color(255);
	private GButton trigButton; 
	private GButton libraryButton; 
	private GButton configButton; 
	private GLabel titleLabel; 
	
	private ArrayList<PHLight> lights;
	
	public void setup() {
		size(750,500, JAVA2D);
		background(colorWhite);
		createGUI();

//		cp5 = new ControlP5(this);
//		noStroke();
//		addButtons();
		context = new SimpleOpenNI(this);
		if (context.isInit() == false) {
			System.out.println("Can't init SimpleOpenNI, check camera.");
			exit();
			return;
		}
		
		context.setMirror(false);
		context.enableDepth();
//		context.enableRGB();
		context.enableUser();

		controller = new KinectLightController();
	}
	
//	public void addButtons() {
//		ControlFont cf = new ControlFont(createFont("Arial",20));
//		makeButton("Trigger/Response Setup", 0, 100, 100, cf);
//	}
//	
//	public Button makeButton(String name, int value, int xPos, int yPos, ControlFont font) {
//		Button b = cp5.addButton(name).setValue(0).setPosition(xPos,yPos).setSize(150,150);
//		b.getCaptionLabel().setFont(font);
//		b.getCaptionLabel();
//		return b;
//	}
	
	public void controlEvent(ControlEvent event) {
		System.out.println(event.getController().getName());
	}
	
	
	public void draw() {
		
		context.update();
	

		for (int user : users) {
			context.getCoM(user, pos);
			if (pos.x > MID_X && current == false) {
				System.out.println("Greater than midpoint");
				
				controller.turnOnRandomLeft();
				controller.turnOffRightLight();
				current = true;
			}
			if (pos.x < MID_X && current == true) {
				controller.turnOffLeftLight();
				controller.turnOnRandomRight();
				current = false;
			}
		}
	}
	
	public void createGUI(){
		  G4P.messagesEnabled(false);
		  G4P.setGlobalColorScheme(GCScheme.BLUE_SCHEME);
		  G4P.setCursor(ARROW);
		  if(frame != null)
		    frame.setTitle("Sketch Window");
		  trigButton = new GButton(this, 40, 100, 120, 120);
		  trigButton.setText("Trigger/Response Setup");
		  trigButton.setTextBold();
		  trigButton.setLocalColorScheme(GCScheme.CYAN_SCHEME);
		  trigButton.addEventHandler(this, "trigClick");
		  libraryButton = new GButton(this, 180, 100, 120, 120);
		  libraryButton.setText("Lights Library");
		  libraryButton.setTextBold();
		  libraryButton.setLocalColorScheme(GCScheme.CYAN_SCHEME);
		  libraryButton.addEventHandler(this, "libraryClick");
		  configButton = new GButton(this, 320, 100, 120, 120);
		  configButton.setText("Bridge Configuration");
		  configButton.setTextBold();
		  configButton.setLocalColorScheme(GCScheme.CYAN_SCHEME);
		  configButton.addEventHandler(this, "configClick");
		  titleLabel = new GLabel(this, 200, 40, 80, 40);
		  titleLabel.setText("Lights App");
		  titleLabel.setTextBold();
		  titleLabel.setOpaque(false);
		}
	
   public void onNewUser(SimpleOpenNI curContext, int userId) {
		users.add(userId);
		curContext.startTrackingSkeleton(userId);
		System.out.println("new user");
	}
   
	   public void trigClick(GButton source, GEvent event) { //_CODE_:trigButton:606518:
	   println("button1 - GButton >> GEvent." + event + " @ " + millis());
	 } //_CODE_:trigButton:606518:
	
	 public void libraryClick(GButton source, GEvent event) { //_CODE_:libraryButton:946758:
	   println("libraryButton - GButton >> GEvent." + event + " @ " + millis());
	 } //_CODE_:libraryButton:946758:
	
	 public void configClick(GButton source, GEvent event) { //_CODE_:configButton:350818:
	   println("configButton - GButton >> GEvent." + event + " @ " + millis());
	 } //_CODE_:configButton:350818:
}
