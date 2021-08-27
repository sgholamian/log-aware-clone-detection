//,temp,sample_4512.java,2,11,temp,sample_4513.java,2,16
//,3
public class xxx {
private void consumeBatchesOnLoop(final Session session, final MessageConsumer consumer) throws JMSException {
final boolean usingTimeout = completionTimeout > 0;
while (running.get()) {
if (timeout.compareAndSet(true, false) || timeoutInterval.compareAndSet(true, false)) {


log.info("completion batch due timeout");
}
}
}

};