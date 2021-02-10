//,temp,CapacitySchedulerConfiguration.java,1878,1905,temp,AbstractReservationSystem.java,432,453
//,3
public class xxx {
  @Private
  protected AutoCreatedQueueManagementPolicy
  getAutoCreatedQueueManagementPolicyClass(
      String queueName) {

    String queueManagementPolicyClassName =
        getAutoCreatedQueueManagementPolicy(queueName);
    LOG.info("Using Auto Created Queue Management Policy: "
        + queueManagementPolicyClassName + " for queue: " + queueName);
    try {
      Class<?> queueManagementPolicyClazz = getClassByName(
          queueManagementPolicyClassName);
      if (AutoCreatedQueueManagementPolicy.class.isAssignableFrom(
          queueManagementPolicyClazz)) {
        return (AutoCreatedQueueManagementPolicy) ReflectionUtils.newInstance(
            queueManagementPolicyClazz, this);
      } else{
        throw new YarnRuntimeException(
            "Class: " + queueManagementPolicyClassName + " not instance of "
                + AutoCreatedQueueManagementPolicy.class.getCanonicalName());
      }
    } catch (ClassNotFoundException e) {
      throw new YarnRuntimeException(
          "Could not instantiate " + "AutoCreatedQueueManagementPolicy: "
              + queueManagementPolicyClassName + " for queue: " + queueName,
          e);
    }
  }

};