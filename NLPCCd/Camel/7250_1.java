//,temp,sample_7366.java,2,13,temp,sample_4360.java,2,12
//,3
public class xxx {
public synchronized boolean sendNoop() throws GenericFileOperationFailedException {
if (isConnected()) {
try {
session.sendKeepAliveMsg();
return true;
} catch (Exception e) {


log.info("sftp session was closed ignoring this exception");
}
}
}

};