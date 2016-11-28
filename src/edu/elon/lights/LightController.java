package edu.elon.lights;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHBridgeResourcesCache;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;

public class LightController {
	
	private PHHueSDK sdk;
	
	private static final int MAX_HUE = 65535;
	public static final int GREEN = 25500;
	public static final int RED = 0;
	public static final int BLUE = 46920;
	public static final int YELLOW = 12750;
	
	
	public LightController(PHBridge bridge, PHHueSDK sdk) {
		this.sdk = sdk;
	}
	
	public void turnOnRandomHue(PHLight light) {
		PHBridge bridge = sdk.getSelectedBridge();
//        PHBridgeResourcesCache cache = bridge.getResourceCache();

//        List<PHLight> allLights = cache.getAllLights();
        Random rand = new Random();

      
        PHLightState lightState = new PHLightState();
        lightState.setOn(true);
        lightState.setHue(rand.nextInt(MAX_HUE));
        bridge.updateLightState(light, lightState); // If no bridge response is required then use this simpler form.
	}
	
	public void turnOffLight(PHLight light) {
		PHBridge bridge = sdk.getSelectedBridge();
		
		PHLightState lightState = new PHLightState();
		lightState.setOn(false);
		bridge.updateLightState(light, lightState);
	}
	
	public void turnOnAllLights(ArrayList<PHLight> lights) {
		for (PHLight light : lights) {
			turnOnRandomHue(light);
		}
	}
	
	
	public void turnLightColor(PHLight light, Integer value) {
	  PHBridge bridge = sdk.getSelectedBridge();
      Random rand = new Random();

     
    
      PHLightState lightState = new PHLightState();
      lightState.setOn(true);
      lightState.setHue(value);
      bridge.updateLightState(light, lightState);
	}

}
