//,temp,AbstractReservationSystem.java,358,379,temp,AbstractReservationSystem.java,180,203
//,3
public class xxx {
  protected Planner getReplanner(String planQueueName) {
    ReservationSchedulerConfiguration reservationConfig =
        getReservationSchedulerConfiguration();
    String plannerClassName = reservationConfig.getReplanner(planQueueName);
    LOG.info("Using Replanner: " + plannerClassName + " for queue: "
        + planQueueName);
    try {
      Class<?> plannerClazz = conf.getClassByName(plannerClassName);
      if (Planner.class.isAssignableFrom(plannerClazz)) {
        Planner planner =
            (Planner) ReflectionUtils.newInstance(plannerClazz, conf);
        planner.init(planQueueName, reservationConfig);
        return planner;
      } else {
        throw new YarnRuntimeException("Class: " + plannerClazz
            + " not instance of " + Planner.class.getCanonicalName());
      }
    } catch (ClassNotFoundException e) {
      throw new YarnRuntimeException("Could not instantiate Planner: "
          + plannerClassName + " for queue: " + planQueueName, e);
    }
  }

};