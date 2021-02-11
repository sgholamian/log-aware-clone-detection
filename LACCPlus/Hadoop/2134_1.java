//,temp,SchedulerNode.java,234,242,temp,SchedulerNode.java,224,232
//,2
public class xxx {
  private synchronized void deductAvailableResource(Resource resource) {
    if (resource == null) {
      LOG.error("Invalid deduction of null resource for "
          + rmNode.getNodeAddress());
      return;
    }
    Resources.subtractFrom(availableResource, resource);
    Resources.addTo(usedResource, resource);
  }

};