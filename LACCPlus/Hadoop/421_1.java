//,temp,LeafQueue.java,1047,1091,temp,AbstractCSQueue.java,447,520
//,3
public class xxx {
  @Private
  protected synchronized boolean canAssignToUser(Resource clusterResource,
      String userName, Resource limit, FiCaSchedulerApp application,
      String nodePartition, ResourceLimits currentResoureLimits) {
    User user = getUser(userName);

    // Note: We aren't considering the current request since there is a fixed
    // overhead of the AM, but it's a > check, not a >= check, so...
    if (Resources
        .greaterThan(resourceCalculator, clusterResource,
            user.getUsed(nodePartition),
            limit)) {
      // if enabled, check to see if could we potentially use this node instead
      // of a reserved node if the application has reserved containers
      if (this.reservationsContinueLooking &&
          nodePartition.equals(CommonNodeLabelsManager.NO_LABEL)) {
        if (Resources.lessThanOrEqual(
            resourceCalculator,
            clusterResource,
            Resources.subtract(user.getUsed(),
                application.getCurrentReservation()), limit)) {

          if (LOG.isDebugEnabled()) {
            LOG.debug("User " + userName + " in queue " + getQueueName()
                + " will exceed limit based on reservations - " + " consumed: "
                + user.getUsed() + " reserved: "
                + application.getCurrentReservation() + " limit: " + limit);
          }
          Resource amountNeededToUnreserve =
              Resources.subtract(user.getUsed(nodePartition), limit);
          // we can only acquire a new container if we unreserve first to
          // respect user-limit
          currentResoureLimits.setAmountNeededUnreserve(amountNeededToUnreserve);
          return true;
        }
      }
      if (LOG.isDebugEnabled()) {
        LOG.debug("User " + userName + " in queue " + getQueueName()
            + " will exceed limit - " + " consumed: "
            + user.getUsed(nodePartition) + " limit: " + limit);
      }
      return false;
    }
    return true;
  }

};