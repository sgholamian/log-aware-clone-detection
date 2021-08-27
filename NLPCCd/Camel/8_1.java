//,temp,sample_4508.java,2,12,temp,sample_1006.java,2,14
//,3
public class xxx {
private void closeJmsSession(Session session) {
try {
session.close();
} catch (JMSException ex2) {
if (log.isDebugEnabled()) {


log.info("exception caught closing session");
}
}
}

};