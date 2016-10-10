package edu.elon.lights;

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

}