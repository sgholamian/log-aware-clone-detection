//,temp,sample_4361.java,2,13,temp,sample_4495.java,2,10
//,3
public class xxx {
public void run() {
jmsConsumerExecutors = getEndpoint().getCamelContext().getExecutorServiceManager().newFixedThreadPool(this, "SjmsBatchConsumer", consumerCount);
consumersShutdownLatchRef.set(new CountDownLatch(consumerCount));
if (completionInterval > 0) {


log.info("using completioninterval to run every millis");
}
}

};