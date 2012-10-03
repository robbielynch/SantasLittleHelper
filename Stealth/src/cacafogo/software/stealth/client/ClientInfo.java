package cacafogo.software.stealth.client;

public class ClientInfo {
	
	private static String clientName = "CacafogoStealth";
	private static String clientUserName = "robbio87";
	private static String clientEmail = "robbio87@gmail.com";
	private static String clientPass = "7803013u";
	
	
	//Incoming SMS Info
	private static String inSmsBody = "";
	private static String inSmsFrom = "";
	private static long inSmsTimeStamp = 0L;
	
	
	
	public static String getClientName() {
		return clientName;
	}
	public static void setClientName(String clientName) {
		ClientInfo.clientName = clientName;
	}
	public static String getClientEmail() {
		return clientEmail;
	}
	public static void setClientEmail(String clientEmail) {
		ClientInfo.clientEmail = clientEmail;
	}
	public static String getClientPass() {
		return clientPass;
	}
	public static void setClientPass(String clientPass) {
		ClientInfo.clientPass = clientPass;
	}
	public static String getClientUserName() {
		return clientUserName;
	}
	public static void setClientUserName(String clientUserName) {
		ClientInfo.clientUserName = clientUserName;
	}
	
	
	//getters and setters for incoming sms
	public static String getInSmsBody() {
		return inSmsBody;
	}
	public static void setInSmsBody(String inSmsBody) {
		ClientInfo.inSmsBody = inSmsBody;
	}
	public static String getInSmsFrom() {
		return inSmsFrom;
	}
	public static void setInSmsFrom(String inSmsFrom) {
		ClientInfo.inSmsFrom = inSmsFrom;
	}
	public static long getInSmsTimeStamp() {
		return inSmsTimeStamp;
	}
	public static void setInSmsTimeStamp(long inSmsTimeStamp) {
		ClientInfo.inSmsTimeStamp = inSmsTimeStamp;
	}

}
