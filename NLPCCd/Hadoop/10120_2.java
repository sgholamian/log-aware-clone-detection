//,temp,sample_3524.java,2,13,temp,sample_3525.java,2,13
//,3
public class xxx {
private CapacitySchedulerConfiguration setupBlockedQueueConfiguration( CapacitySchedulerConfiguration conf) {
conf.setQueues(CapacitySchedulerConfiguration.ROOT, new String[]{"a", "b"});
conf.setCapacity(A, 80f);
conf.setCapacity(B, 20f);
conf.setUserLimitFactor(A, 100);
conf.setUserLimitFactor(B, 100);
conf.setMaximumCapacity(A, 100);
conf.setMaximumCapacity(B, 100);


log.info("setup top level queues a and b");
}

};