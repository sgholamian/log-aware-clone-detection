//,temp,FSAppAttempt.java,181,208,temp,FiCaSchedulerApp.java,664,698
//,3
public class xxx {
  private void unreserveInternal(
      SchedulerRequestKey schedulerKey, FSSchedulerNode node) {
    try {
      writeLock.lock();
      Map<NodeId, RMContainer> reservedContainers = this.reservedContainers.get(
          schedulerKey);
      RMContainer reservedContainer = reservedContainers.remove(
          node.getNodeID());
      if (reservedContainers.isEmpty()) {
        this.reservedContainers.remove(schedulerKey);
      }

      // Reset the re-reservation count
      resetReReservations(schedulerKey);

      Resource resource = reservedContainer.getContainer().getResource();
      this.attemptResourceUsage.decReserved(resource);

      LOG.info(
          "Application " + getApplicationId() + " unreserved " + " on node "
              + node + ", currently has " + reservedContainers.size()
              + " at priority " + schedulerKey.getPriority()
              + "; currentReservation " + this.attemptResourceUsage
              .getReserved());
    } finally {
      writeLock.unlock();
    }
  }

};