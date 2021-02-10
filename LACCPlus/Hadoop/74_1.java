//,temp,AbstractReservationSystem.java,400,421,temp,AbstractReservationSystem.java,180,203
//,3
public class xxx {
  protected SharingPolicy getAdmissionPolicy(String queueName) {
    ReservationSchedulerConfiguration reservationConfig =
        getReservationSchedulerConfiguration();
    String admissionPolicyClassName =
        reservationConfig.getReservationAdmissionPolicy(queueName);
    LOG.info("Using AdmissionPolicy: " + admissionPolicyClassName
        + " for queue: " + queueName);
    try {
      Class<?> admissionPolicyClazz =
          conf.getClassByName(admissionPolicyClassName);
      if (SharingPolicy.class.isAssignableFrom(admissionPolicyClazz)) {
        return (SharingPolicy) ReflectionUtils.newInstance(
            admissionPolicyClazz, conf);
      } else {
        throw new YarnRuntimeException("Class: " + admissionPolicyClassName
            + " not instance of " + SharingPolicy.class.getCanonicalName());
      }
    } catch (ClassNotFoundException e) {
      throw new YarnRuntimeException("Could not instantiate AdmissionPolicy: "
          + admissionPolicyClassName + " for queue: " + queueName, e);
    }
  }

};