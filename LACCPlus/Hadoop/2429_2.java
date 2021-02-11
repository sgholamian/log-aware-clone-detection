//,temp,FSAppAttempt.java,181,208,temp,FiCaSchedulerApp.java,664,698
//,3
public class xxx {
  private boolean internalUnreserve(FiCaSchedulerNode node,
      SchedulerRequestKey schedulerKey) {
    Map<NodeId, RMContainer> reservedContainers =
        this.reservedContainers.get(schedulerKey);

    if (reservedContainers != null) {
      RMContainer reservedContainer =
          reservedContainers.remove(node.getNodeID());

      // unreserve is now triggered in new scenarios (preemption)
      // as a consequence reservedcontainer might be null, adding NP-checks
      if (reservedContainer != null
          && reservedContainer.getContainer() != null
          && reservedContainer.getContainer().getResource() != null) {

        if (reservedContainers.isEmpty()) {
          this.reservedContainers.remove(schedulerKey);
        }
        // Reset the re-reservation count
        resetReReservations(schedulerKey);

        Resource resource = reservedContainer.getReservedResource();
        this.attemptResourceUsage.decReserved(node.getPartition(), resource);

        LOG.info("Application " + getApplicationId() + " unreserved "
            + " on node " + node + ", currently has "
            + reservedContainers.size()
            + " at priority " + schedulerKey.getPriority()
            + "; currentReservation " + this.attemptResourceUsage.getReserved()
            + " on node-label=" + node.getPartition());
        return true;
      }
    }
    return false;
  }

};