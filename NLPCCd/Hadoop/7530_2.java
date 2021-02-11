//,temp,sample_3525.java,2,13,temp,sample_9435.java,2,13
//,3
public class xxx {
private void setupQueueConfiguration(CapacitySchedulerConfiguration conf) {
conf.setQueues(CapacitySchedulerConfiguration.ROOT, new String[] {A, B});
final String Q_A = CapacitySchedulerConfiguration.ROOT + "." + A;
conf.setCapacity(Q_A, 10);
final String Q_B = CapacitySchedulerConfiguration.ROOT + "." + B;
conf.setCapacity(Q_B, 90);
conf.setUserLimit(CapacitySchedulerConfiguration.ROOT + "." + A, 50);
conf.setUserLimitFactor(CapacitySchedulerConfiguration.ROOT + "." + A, 5.0f);


log.info("setup top level queues a and b");
}

};