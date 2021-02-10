//,temp,AMRMClientImpl.java,692,757,temp,RMContainerRequestor.java,464,514
//,3
public class xxx {
  private void decResourceRequest(Priority priority, 
                                   String resourceName,
                                   Resource capability, 
                                   T req) {
    Map<String, TreeMap<Resource, ResourceRequestInfo>> remoteRequests =
      this.remoteRequestsTable.get(priority);
    
    if(remoteRequests == null) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("Not decrementing resource as priority " + priority 
            + " is not present in request table");
      }
      return;
    }
    
    Map<Resource, ResourceRequestInfo> reqMap = remoteRequests.get(resourceName);
    if (reqMap == null) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("Not decrementing resource as " + resourceName
            + " is not present in request table");
      }
      return;
    }
    ResourceRequestInfo resourceRequestInfo = reqMap.get(capability);

    if (LOG.isDebugEnabled()) {
      LOG.debug("BEFORE decResourceRequest:" + " applicationId="
          + " priority=" + priority.getPriority()
          + " resourceName=" + resourceName + " numContainers="
          + resourceRequestInfo.remoteRequest.getNumContainers() 
          + " #asks=" + ask.size());
    }

    resourceRequestInfo.remoteRequest.setNumContainers(
        resourceRequestInfo.remoteRequest.getNumContainers() - 1);

    resourceRequestInfo.containerRequests.remove(req);
    
    if(resourceRequestInfo.remoteRequest.getNumContainers() < 0) {
      // guard against spurious removals
      resourceRequestInfo.remoteRequest.setNumContainers(0);
    }
    // send the ResourceRequest to RM even if is 0 because it needs to override
    // a previously sent value. If ResourceRequest was not sent previously then
    // sending 0 aught to be a no-op on RM
    addResourceRequestToAsk(resourceRequestInfo.remoteRequest);

    // delete entries from map if no longer needed
    if (resourceRequestInfo.remoteRequest.getNumContainers() == 0) {
      reqMap.remove(capability);
      if (reqMap.size() == 0) {
        remoteRequests.remove(resourceName);
      }
      if (remoteRequests.size() == 0) {
        remoteRequestsTable.remove(priority);
      }
    }

    if (LOG.isDebugEnabled()) {
      LOG.info("AFTER decResourceRequest:" + " applicationId="
          + " priority=" + priority.getPriority()
          + " resourceName=" + resourceName + " numContainers="
          + resourceRequestInfo.remoteRequest.getNumContainers() 
          + " #asks=" + ask.size());
    }
  }

};