//,temp,sample_4506.java,2,12,temp,sample_943.java,2,10
//,3
public class xxx {
private void closeJmsConsumer(MessageConsumer consumer) {
try {
consumer.close();
} catch (JMSException ex2) {
if (log.isDebugEnabled()) {


log.info("exception caught closing consumer");
}
}
}

};