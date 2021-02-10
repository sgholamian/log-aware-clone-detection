//,temp,TestApplicationLimits.java,131,146,temp,TestParentQueue.java,104,116
//,3
public class xxx {
  private void setupQueueConfiguration(CapacitySchedulerConfiguration conf) {
    
    // Define top-level queues
    conf.setQueues(CapacitySchedulerConfiguration.ROOT, new String[] {A, B});

    final String Q_A = CapacitySchedulerConfiguration.ROOT + "." + A;
    conf.setCapacity(Q_A, 10);
    
    final String Q_B = CapacitySchedulerConfiguration.ROOT + "." + B;
    conf.setCapacity(Q_B, 90);
    
    conf.setUserLimit(CapacitySchedulerConfiguration.ROOT + "." + A, 50);
    conf.setUserLimitFactor(CapacitySchedulerConfiguration.ROOT + "." + A, 5.0f);
    
    LOG.info("Setup top-level queues a and b");
  }

};