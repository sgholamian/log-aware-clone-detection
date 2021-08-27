//,temp,sample_2843.java,2,13,temp,sample_1568.java,2,15
//,3
public class xxx {
protected void connectIfNecessary() throws GenericFileOperationFailedException {
if (!loggedIn || !getOperations().isConnected()) {
RemoteFileConfiguration config = getEndpoint().getConfiguration();
loggedIn = getOperations().connect(config);
if (!loggedIn) {
return;
}


log.info("connected and logged in to");
}
}

};