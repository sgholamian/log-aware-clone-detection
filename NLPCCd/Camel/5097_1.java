//,temp,sample_4507.java,2,12,temp,sample_1572.java,2,15
//,3
public class xxx {
private void closeJmsConsumer(MessageConsumer consumer) {
try {
consumer.close();
} catch (JMSException ex2) {
if (log.isDebugEnabled()) {
}


log.info("exception caught closing consumer this exception is ignored");
}
}

};