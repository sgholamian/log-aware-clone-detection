//,temp,QueueManagementDynamicEditPolicy.java,90,116,temp,ProportionalCapacityPreemptionPolicy.java,158,172
//,3
public class xxx {
  public void init(Configuration config, RMContext context,
      ResourceScheduler sched) {
    LOG.info("Preemption monitor:" + this.getClass().getCanonicalName());
    assert null == scheduler : "Unexpected duplicate call to init";
    if (!(sched instanceof CapacityScheduler)) {
      throw new YarnRuntimeException("Class " +
          sched.getClass().getCanonicalName() + " not instance of " +
          CapacityScheduler.class.getCanonicalName());
    }
    rmContext = context;
    scheduler = (CapacityScheduler) sched;
    rc = scheduler.getResourceCalculator();
    nlm = scheduler.getRMContext().getNodeLabelManager();
    updateConfigIfNeeded();
  }

};