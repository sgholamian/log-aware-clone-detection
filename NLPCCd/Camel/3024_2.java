//,temp,sample_1570.java,2,13,temp,sample_1573.java,2,13
//,3
public class xxx {
protected void connectIfNecessary() throws IOException {
boolean isConnected = false;
try {
isConnected = getOperations().sendNoop();
} catch (Exception ex) {
if (log.isDebugEnabled()) {


log.info("exception checking connection status");
}
}
}

};