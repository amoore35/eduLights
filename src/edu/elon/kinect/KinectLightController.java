package edu.elon.kinect;

import java.util.ArrayList;
import java.util.List;

import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.model.PHLight;

import edu.elon.lights.LightConnection;
import edu.elon.lights.LightController;
import edu.elon.lights.data.LightsProperties;

public class KinectLightController {

	private PHHueSDK sdk;
	private LightController controller;
	private LightConnection connection;
	private List<PHLight> lights;
	private PHLight leftLight;
	private PHLight rightLight1;
	private PHLight rightLight2;
	
	public KinectLightController() {
		sdk = PHHueSDK.create();
		LightsProperties.loadProperties();
		
		connection = new LightConnection();
		sdk.getNotificationManager().registerSDKListener(connection.getListener());
		connection.findBridges();
		connection.connect();
		
		
		
		
		controller = connection.getLightController();
//		lights = connection.getLights();
//		leftLight = lights.get(2);
//		rightLight1 = lights.get(1);
//		rightLight2 = lights.get(0);
	}
	
//	public void setUpLights() {
//		lights = connection.getLights();
//		leftLight = lights.get(2);
//		rightLight1 = lights.get(1);
//		rightLight2 = lights.get(0);
//	}
	
	public void turnOnRandomLeft() {
		List<PHLight> lights = connection.getLights();
				
		controller.turnOnRandomHue(lights.get(2));
	}
	
	public void turnOnRandomRight() {
		List<PHLight> lights = connection.getLights();
		
		controller.turnOnRandomHue(lights.get(1));
	}
	
	public void turnOffLeftLight() {
		List<PHLight> lights = connection.getLights();
		
		controller.turnOffLight(lights.get(2));
	}
	
	public void turnOffRightLight() {
        List<PHLight> lights = connection.getLights();
		
		controller.turnOffLight(lights.get(1));
	}
	
}
