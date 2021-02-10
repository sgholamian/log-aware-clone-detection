//,temp,SchedulerNode.java,234,242,temp,SchedulerNode.java,224,232
//,2
public class xxx {
  private synchronized void addAvailableResource(Resource resource) {
    if (resource == null) {
      LOG.error("Invalid resource addition of null resource for "
          + rmNode.getNodeAddress());
      return;
    }
    Resources.addTo(availableResource, resource);
    Resources.subtractFrom(usedResource, resource);
  }

};