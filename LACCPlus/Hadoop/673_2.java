//,temp,AbstractReservationSystem.java,358,379,temp,AbstractReservationSystem.java,180,203
//,3
public class xxx {
  private PlanFollower createPlanFollower() {
    String planFollowerPolicyClassName =
        conf.get(YarnConfiguration.RM_RESERVATION_SYSTEM_PLAN_FOLLOWER,
            getDefaultPlanFollower());
    if (planFollowerPolicyClassName == null) {
      return null;
    }
    LOG.info("Using PlanFollowerPolicy: " + planFollowerPolicyClassName);
    try {
      Class<?> planFollowerPolicyClazz =
          conf.getClassByName(planFollowerPolicyClassName);
      if (PlanFollower.class.isAssignableFrom(planFollowerPolicyClazz)) {
        return (PlanFollower) ReflectionUtils.newInstance(
            planFollowerPolicyClazz, conf);
      } else {
        throw new YarnRuntimeException("Class: " + planFollowerPolicyClassName
            + " not instance of " + PlanFollower.class.getCanonicalName());
      }
    } catch (ClassNotFoundException e) {
      throw new YarnRuntimeException(
          "Could not instantiate PlanFollowerPolicy: "
              + planFollowerPolicyClassName, e);
    }
  }

};