//,temp,ResourceManager.java,433,450,temp,AbstractReservationSystem.java,246,270
//,3
public class xxx {
  protected ResourceScheduler createScheduler() {
    String schedulerClassName = conf.get(YarnConfiguration.RM_SCHEDULER,
        YarnConfiguration.DEFAULT_RM_SCHEDULER);
    LOG.info("Using Scheduler: " + schedulerClassName);
    try {
      Class<?> schedulerClazz = Class.forName(schedulerClassName);
      if (ResourceScheduler.class.isAssignableFrom(schedulerClazz)) {
        return (ResourceScheduler) ReflectionUtils.newInstance(schedulerClazz,
            this.conf);
      } else {
        throw new YarnRuntimeException("Class: " + schedulerClassName
            + " not instance of " + ResourceScheduler.class.getCanonicalName());
      }
    } catch (ClassNotFoundException e) {
      throw new YarnRuntimeException("Could not instantiate Scheduler: "
          + schedulerClassName, e);
    }
  }

};