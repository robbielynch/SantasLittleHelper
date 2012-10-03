package cacafogo.software.stealth;

import cacafogo.software.stealth.broadcast_receiver.SmsReceiver;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class StealthActivity extends Activity {
	
	private String editTextShowLocation;

	private LocationManager locManager;
	private LocationListener locListener = new MyLocationListener();

	private boolean gps_enabled = false;
	private boolean network_enabled = false;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        //start location receiver
        locManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        goForLocation();
        
        
        
        //start the sms reveiver listener
        Intent smsReceiveIntent = new Intent(this, SmsReceiver.class);
        sendBroadcast(smsReceiveIntent);
        
	}

	private void goForLocation() {
		
		System.out.println("in Go For Location");
		
		// exceptions will be thrown if provider is not permitted.
		try {
			gps_enabled = locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		} catch (Exception ex) {
		}
		try {
			network_enabled = locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		} catch (Exception ex) {
		}

		// don't start listeners if no provider is enabled
		if (!gps_enabled && !network_enabled) {
			System.out.println("Problem... gps not enabled, network not enabled");
		}

		if (gps_enabled) {
			locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locListener);
		}
		if (network_enabled) {
			locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locListener);
		}
		
		
	}

	

	class MyLocationListener implements LocationListener {
		@Override
		public void onLocationChanged(Location location) {
			if (location != null) {
				// This needs to stop getting the location data and save the battery power.
				locManager.removeUpdates(locListener); 

				String longitude = "Londitude: " + location.getLongitude();
				String latitude = "Latitude: " + location.getLatitude();
				String altitiude = "Altitiude: " + location.getAltitude();
				String accuracy = "Accuracy: " + location.getAccuracy();
				String time = "Time: " + location.getTime();
				
				
				//location details
				editTextShowLocation = longitude + "\n" + latitude + "\n" + altitiude + "\n" + accuracy + "\n" + time;
				Log.d("Location", editTextShowLocation);
				
				//set global attributes
				Global.latitude = location.getLatitude() + "";
				Global.longitude = editTextShowLocation;

			}
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}
	}

	/*
	@Override
	public void onClick(DialogInterface dialog, int which) {
		if(which == DialogInterface.BUTTON_NEUTRAL){
			editTextShowLocation.setText("Sorry, location is not determined. To fix this please enable location providers");
		}else if (which == DialogInterface.BUTTON_POSITIVE) {
			
		}
	}*/

}