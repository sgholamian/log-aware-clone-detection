//,temp,sample_397.java,2,11,temp,sample_9435.java,2,13
//,3
public class xxx {
private void setupSingleLevelQueues(CapacitySchedulerConfiguration conf) {
conf.setQueues(CapacitySchedulerConfiguration.ROOT, new String[] {A, B});
final String Q_A = CapacitySchedulerConfiguration.ROOT + "." + A;
conf.setCapacity(Q_A, 30);
final String Q_B = CapacitySchedulerConfiguration.ROOT + "." + B;
conf.setCapacity(Q_B, 70);


log.info("setup top level queues a and b");
}

};