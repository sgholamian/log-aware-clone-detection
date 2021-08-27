//,temp,sample_4506.java,2,12,temp,sample_943.java,2,10
//,3
public class xxx {
public void sessionClosed(IoSession session) throws Exception {
if (sync && !messageReceived) {
if (LOG.isDebugEnabled()) {


log.info("session closed but no message received from address");
}
}
}

};