//,temp,sample_4509.java,2,12,temp,sample_3106.java,2,10
//,3
public class xxx {
private void closeJmsSession(Session session) {
try {
session.close();
} catch (JMSException ex2) {
if (log.isDebugEnabled()) {
}


log.info("exception caught closing session this exception is ignored");
}
}

};