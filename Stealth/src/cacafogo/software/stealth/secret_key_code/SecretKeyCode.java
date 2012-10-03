package cacafogo.software.stealth.secret_key_code;

import cacafogo.software.stealth.Global;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SecretKeyCode {
	
	private String SecretKeyCode;
	private Context context;
	public boolean secretKeyCodeFound = false;
	
	public SecretKeyCode(){
		
	}
	
	
	public SecretKeyCode(String secretKeyCode, Context context){
		this.SecretKeyCode = secretKeyCode;
		this.context = context;
		
		//start the checks for each secretKeyCode
		checkForSecretKeyCode();
	}


	//this is where we check for specific sms codes and do the appropriate thing from there
	private void checkForSecretKeyCode() {
		
		if(SecretKeyCode.equalsIgnoreCase("secretKeyCode")){
			//make your actions
	        //and no alert notification and sms not in inbox
	    	Log.d("cacafogo", "secret code has been activated");
	    	
	    	Intent launch = new Intent(context, SecretKeyCodeActivatedActivity.class);
	    	launch.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    	context.startActivity(launch);
	    	
	    	secretKeyCodeFound = true;
		}
		
		else if(SecretKeyCode.equalsIgnoreCase("getCurrentLocation")){
			System.out.println("In getCurrentLocation");
			
			
			
			System.out.println("long = " + Global.longitude);
			System.out.println("lat = " + Global.longitude);
			
			secretKeyCodeFound = true;
		}
	}

}
