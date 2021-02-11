//,temp,sample_2645.java,2,17,temp,sample_2632.java,2,17
//,2
public class xxx {
public void dummy_method(){
long oldNetBytesReceived = userStats.getNetBytesReceived();
long oldCurrentBytesSent = userStats.getCurrentBytesSent();
long oldCurrentBytesReceived = userStats.getCurrentBytesReceived();
String warning = "Received an external network stats byte count that was less than the stored value. Zone ID: " + userStats.getDataCenterId() + ", account ID: " + userStats.getAccountId() + ".";
userStats.setCurrentBytesSent(newCurrentBytesSent);
if (oldCurrentBytesSent > newCurrentBytesSent) {
userStats.setNetBytesSent(oldNetBytesSent + oldCurrentBytesSent);
}
userStats.setCurrentBytesReceived(newCurrentBytesReceived);
if (oldCurrentBytesReceived > newCurrentBytesReceived) {


log.info("stored bytes received new bytes received");
}
}

};