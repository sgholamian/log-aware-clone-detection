//,temp,TestApplicationLimits.java,131,146,temp,TestParentQueue.java,104,116
//,3
public class xxx {
  private void setupSingleLevelQueues(CapacitySchedulerConfiguration conf) {
    
    // Define top-level queues
    conf.setQueues(CapacitySchedulerConfiguration.ROOT, new String[] {A, B});
    
    final String Q_A = CapacitySchedulerConfiguration.ROOT + "." + A;
    conf.setCapacity(Q_A, 30);
    
    final String Q_B = CapacitySchedulerConfiguration.ROOT + "." + B;
    conf.setCapacity(Q_B, 70);
    
    LOG.info("Setup top-level queues a and b");
  }

};