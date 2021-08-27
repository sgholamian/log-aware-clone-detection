//,temp,sample_1570.java,2,13,temp,sample_1573.java,2,13
//,3
public class xxx {
protected void forceDisconnect() {
loggedIn = false;
try {
if (log.isDebugEnabled()) {
}
getOperations().forceDisconnect();
} catch (GenericFileOperationFailedException e) {


log.info("error occurred while disconnecting from due this exception will be ignored");
}
}

};