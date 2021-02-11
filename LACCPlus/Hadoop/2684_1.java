//,temp,CompositeService.java,72,79,temp,FSQueue.java,289,296
//,3
public class xxx {
  protected void addService(Service service) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("Adding service " + service.getName());
    }
    synchronized (serviceList) {
      serviceList.add(service);
    }
  }

};