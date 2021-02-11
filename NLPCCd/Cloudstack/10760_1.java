//,temp,sample_2675.java,2,15,temp,sample_7627.java,2,15
//,3
public class xxx {
public EmailAlert(String[] recipientList, String smtpHost, int smtpPort, int smtpConnectionTimeout, int smtpTimeout, boolean smtpUseAuth, final String smtpUsername, final String smtpPassword, String emailSender, boolean smtpDebug) {
if (recipientList != null) {
_recipientList = new InternetAddress[recipientList.length];
for (int i = 0; i < recipientList.length; i++) {
try {
_recipientList[i] = new InternetAddress(recipientList[i], recipientList[i]);
} catch (Exception ex) {


log.info("exception creating address for");
}
}
}
}

};