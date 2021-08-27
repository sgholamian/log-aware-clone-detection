//,temp,sample_5643.java,2,10,temp,sample_6323.java,2,10
//,3
public class xxx {
protected void doStop() throws Exception {
if (sessionId.get() != null) {
if (keyValueClient.releaseLock(this.path, sessionId.get())) {


log.info("successfully released lock on path with id");
}
}
}

};