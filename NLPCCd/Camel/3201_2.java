//,temp,sample_2843.java,2,13,temp,sample_1568.java,2,15
//,3
public class xxx {
protected void disconnect() {
loggedIn = false;
try {
if (getOperations().isConnected()) {
if (log.isDebugEnabled()) {
}
getOperations().disconnect();
}
} catch (GenericFileOperationFailedException e) {


log.info("error occurred while disconnecting from due this exception will be ignored");
}
}

};