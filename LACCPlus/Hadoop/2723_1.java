//,temp,TestCapacityScheduler.java,785,800,temp,TestCapacityScheduler.java,657,676
//,3
public class xxx {
  private CapacitySchedulerConfiguration setupBlockedQueueConfiguration(
      CapacitySchedulerConfiguration conf) {

    // Define top-level queues
    conf.setQueues(CapacitySchedulerConfiguration.ROOT,
        new String[]{"a", "b"});

    conf.setCapacity(A, 80f);
    conf.setCapacity(B, 20f);
    conf.setUserLimitFactor(A, 100);
    conf.setUserLimitFactor(B, 100);
    conf.setMaximumCapacity(A, 100);
    conf.setMaximumCapacity(B, 100);
    LOG.info("Setup top-level queues a and b");
    return conf;
  }

};