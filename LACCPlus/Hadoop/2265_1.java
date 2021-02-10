//,temp,QueueManagementDynamicEditPolicy.java,90,116,temp,ProportionalCapacityPreemptionPolicy.java,158,172
//,3
public class xxx {
  @Override
  public void init(final Configuration config, final RMContext context,
      final ResourceScheduler sched) {
    LOG.info("Queue Management Policy monitor:" + this.
        getClass().getCanonicalName());
    assert null == scheduler : "Unexpected duplicate call to init";
    if (!(sched instanceof CapacityScheduler)) {
      throw new YarnRuntimeException("Class " +
          sched.getClass().getCanonicalName() + " not instance of " +
          CapacityScheduler.class.getCanonicalName());
    }
    rmContext = context;
    scheduler = (CapacityScheduler) sched;
    clock = scheduler.getClock();

    rc = scheduler.getResourceCalculator();
    nlm = scheduler.getRMContext().getNodeLabelManager();

    CapacitySchedulerConfiguration csConfig = scheduler.getConfiguration();

    monitoringInterval = csConfig.getLong(
        CapacitySchedulerConfiguration.QUEUE_MANAGEMENT_MONITORING_INTERVAL,
        CapacitySchedulerConfiguration.
            DEFAULT_QUEUE_MANAGEMENT_MONITORING_INTERVAL);

    initQueues();
  }

};