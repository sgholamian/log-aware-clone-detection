//,temp,TestCapacitySchedulerDynamicBehavior.java,270,292,temp,TestCapacityScheduler.java,687,712
//,3
public class xxx {
  private CapacitySchedulerConfiguration setupQueueConfigurationWithOutB1(
      CapacitySchedulerConfiguration conf) {

    // Define top-level queues
    conf.setQueues(CapacitySchedulerConfiguration.ROOT,
        new String[] { "a", "b" });

    conf.setCapacity(A, A_CAPACITY);
    conf.setCapacity(B, B_CAPACITY);

    // Define 2nd-level queues
    conf.setQueues(A, new String[] { "a1", "a2" });
    conf.setCapacity(A1, A1_CAPACITY);
    conf.setUserLimitFactor(A1, 100.0f);
    conf.setCapacity(A2, A2_CAPACITY);
    conf.setUserLimitFactor(A2, 100.0f);

    conf.setQueues(B, new String[] { "b2", "b3" });
    conf.setCapacity(B2, B2_CAPACITY + B1_CAPACITY); //as B1 is deleted
    conf.setUserLimitFactor(B2, 100.0f);
    conf.setCapacity(B3, B3_CAPACITY);
    conf.setUserLimitFactor(B3, 100.0f);

    LOG.info("Setup top-level queues a and b (without b3)");
    return conf;
  }

};