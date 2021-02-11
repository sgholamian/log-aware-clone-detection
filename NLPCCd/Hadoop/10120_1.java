//,temp,sample_3524.java,2,13,temp,sample_3525.java,2,13
//,3
public class xxx {
private CapacitySchedulerConfiguration setupQueueConfigurationWithOutB( CapacitySchedulerConfiguration conf) {
conf.setQueues(CapacitySchedulerConfiguration.ROOT, new String[] { "a" });
conf.setCapacity(A, A_CAPACITY + B_CAPACITY);
conf.setQueues(A, new String[] { "a1", "a2" });
conf.setCapacity(A1, A1_CAPACITY);
conf.setUserLimitFactor(A1, 100.0f);
conf.setCapacity(A2, A2_CAPACITY);
conf.setUserLimitFactor(A2, 100.0f);


log.info("setup top level queues a");
}

};