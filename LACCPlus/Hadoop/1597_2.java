//,temp,AMRMClientImpl.java,692,757,temp,RMContainerRequestor.java,464,514
//,3
public class xxx {
  private void decResourceRequest(Priority priority, String resourceName,
      Resource capability) {
    Map<String, Map<Resource, ResourceRequest>> remoteRequests =
      this.remoteRequestsTable.get(priority);
    Map<Resource, ResourceRequest> reqMap = remoteRequests.get(resourceName);
    if (reqMap == null) {
      // as we modify the resource requests by filtering out blacklisted hosts 
      // when they are added, this value may be null when being 
      // decremented
      if (LOG.isDebugEnabled()) {
        LOG.debug("Not decrementing resource as " + resourceName
            + " is not present in request table");
      }
      return;
    }
    ResourceRequest remoteRequest = reqMap.get(capability);

    if (LOG.isDebugEnabled()) {
      LOG.debug("BEFORE decResourceRequest:" + " applicationId="
          + applicationId.getId() + " priority=" + priority.getPriority()
          + " resourceName=" + resourceName + " numContainers="
          + remoteRequest.getNumContainers() + " #asks=" + ask.size());
    }

    if(remoteRequest.getNumContainers() > 0) {
      // based on blacklisting comments above we can end up decrementing more 
      // than requested. so guard for that.
      remoteRequest.setNumContainers(remoteRequest.getNumContainers() -1);
    }
    
    if (remoteRequest.getNumContainers() == 0) {
      reqMap.remove(capability);
      if (reqMap.size() == 0) {
        remoteRequests.remove(resourceName);
      }
      if (remoteRequests.size() == 0) {
        remoteRequestsTable.remove(priority);
      }
    }

    // send the updated resource request to RM
    // send 0 container count requests also to cancel previous requests
    addResourceRequestToAsk(remoteRequest);

    if (LOG.isDebugEnabled()) {
      LOG.info("AFTER decResourceRequest:" + " applicationId="
          + applicationId.getId() + " priority=" + priority.getPriority()
          + " resourceName=" + resourceName + " numContainers="
          + remoteRequest.getNumContainers() + " #asks=" + ask.size());
    }
  }

};