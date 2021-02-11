//,temp,TestCapacitySchedulerDynamicBehavior.java,270,292,temp,TestCapacityScheduler.java,765,782
//,3
public class xxx {
  private CapacitySchedulerConfiguration setupQueueConfigurationWithOutB(
      CapacitySchedulerConfiguration conf) {

    // Define top-level queues
    conf.setQueues(CapacitySchedulerConfiguration.ROOT, new String[] { "a" });

    conf.setCapacity(A, A_CAPACITY + B_CAPACITY);

    // Define 2nd-level queues
    conf.setQueues(A, new String[] { "a1", "a2" });
    conf.setCapacity(A1, A1_CAPACITY);
    conf.setUserLimitFactor(A1, 100.0f);
    conf.setCapacity(A2, A2_CAPACITY);
    conf.setUserLimitFactor(A2, 100.0f);

    LOG.info("Setup top-level queues a");
    return conf;
  }

};