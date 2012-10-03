package cacafogo.software.stealth.broadcast_receiver.observer;

public class SmsInfo {
	
	private static String lastSmsSent = "";
	private static String lastSmsReceived = "";
	private static String lastSmsSentSender = "";
	private static long lastSmsSentDate = 0L;
	private static String lastSmsSSentFrom = "";
	
	
	//getters and setters
	public static String getLastSmsSent() {
		return lastSmsSent;
	}
	public static void setLastSmsSent(String lastSmsSent) {
		SmsInfo.lastSmsSent = lastSmsSent;
	}
	public static String getLastSmsReceived() {
		return lastSmsReceived;
	}
	public static void setLastSmsReceived(String lastSmsReceived) {
		SmsInfo.lastSmsReceived = lastSmsReceived;
	}
	public static String getLastSmsSentSender() {
		return lastSmsSentSender;
	}
	public static void setLastSmsSentSender(String lastSmsSentSender) {
		SmsInfo.lastSmsSentSender = lastSmsSentSender;
	}
	public static long getLastSmsSentDate() {
		return lastSmsSentDate;
	}
	public static void setLastSmsSentDate(long lastSmsSentDate) {
		SmsInfo.lastSmsSentDate = lastSmsSentDate;
	}
	public static String getLastSmsSSentFrom() {
		return lastSmsSSentFrom;
	}
	public static void setLastSmsSSentFrom(String lastSmsSSentFrom) {
		SmsInfo.lastSmsSSentFrom = lastSmsSSentFrom;
	}
	

}
