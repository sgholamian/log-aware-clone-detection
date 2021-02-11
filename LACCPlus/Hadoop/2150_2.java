//,temp,AbstractReservationSystem.java,400,421,temp,AbstractReservationSystem.java,381,398
//,2
public class xxx {
  protected ReservationAgent getAgent(String queueName) {
    ReservationSchedulerConfiguration reservationConfig =
        getReservationSchedulerConfiguration();
    String agentClassName = reservationConfig.getReservationAgent(queueName);
    LOG.info("Using Agent: " + agentClassName + " for queue: " + queueName);
    try {
      Class<?> agentClazz = conf.getClassByName(agentClassName);
      if (ReservationAgent.class.isAssignableFrom(agentClazz)) {
        return (ReservationAgent) ReflectionUtils.newInstance(agentClazz, conf);
      } else {
        throw new YarnRuntimeException("Class: " + agentClassName
            + " not instance of " + ReservationAgent.class.getCanonicalName());
      }
    } catch (ClassNotFoundException e) {
      throw new YarnRuntimeException("Could not instantiate Agent: "
          + agentClassName + " for queue: " + queueName, e);
    }
  }

};