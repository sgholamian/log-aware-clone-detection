//,temp,TestCapacityScheduler.java,657,676,temp,TestCapacityScheduler.java,620,646
//,3
public class xxx {
  private CapacitySchedulerConfiguration setupQueueConfWithOutChildrenOfB(
      CapacitySchedulerConfiguration conf) {

    // Define top-level queues
    conf.setQueues(CapacitySchedulerConfiguration.ROOT,
        new String[] {"a","b"});

    conf.setCapacity(A, A_CAPACITY);
    conf.setCapacity(B, B_CAPACITY);

    // Define 2nd-level queues
    conf.setQueues(A, new String[] {"a1","a2"});
    conf.setCapacity(A1, A1_CAPACITY);
    conf.setUserLimitFactor(A1, 100.0f);
    conf.setCapacity(A2, A2_CAPACITY);
    conf.setUserLimitFactor(A2, 100.0f);

    LOG.info("Setup top-level queues a and b (without children)");
    return conf;
  }

};