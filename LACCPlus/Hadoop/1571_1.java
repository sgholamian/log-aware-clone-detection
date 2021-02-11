//,temp,TestCapacitySchedulerDynamicBehavior.java,259,281,temp,TestCapacityScheduler.java,378,404
//,3
public class xxx {
  private void setupPlanQueueConfiguration(CapacitySchedulerConfiguration conf) {

    conf.setQueues(CapacitySchedulerConfiguration.ROOT,
        new String[] { "a", "b" });

    conf.setCapacity(A, A_CAPACITY);
    conf.setCapacity(B, B_CAPACITY);

    // Define 2nd-level queues
    conf.setQueues(B, new String[] { "b1", "b2", "b3" });
    conf.setCapacity(B1, B1_CAPACITY);
    conf.setUserLimitFactor(B1, 100.0f);
    conf.setCapacity(B2, B2_CAPACITY);
    conf.setUserLimitFactor(B2, 100.0f);
    conf.setCapacity(B3, B3_CAPACITY);
    conf.setUserLimitFactor(B3, 100.0f);

    conf.setReservable(A, true);
    conf.setReservationWindow(A, 86400 * 1000);
    conf.setAverageCapacity(A, 1.0f);

    LOG.info("Setup a as a plan queue");
  }

};