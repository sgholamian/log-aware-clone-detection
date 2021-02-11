//,temp,AMRMClientImpl.java,639,690,temp,RMContainerRequestor.java,426,462
//,3
public class xxx {
  private void
      addResourceRequest(Priority priority, String resourceName,
          Resource capability, T req, boolean relaxLocality,
          String labelExpression) {
    Map<String, TreeMap<Resource, ResourceRequestInfo>> remoteRequests =
      this.remoteRequestsTable.get(priority);
    if (remoteRequests == null) {
      remoteRequests = 
          new HashMap<String, TreeMap<Resource, ResourceRequestInfo>>();
      this.remoteRequestsTable.put(priority, remoteRequests);
      if (LOG.isDebugEnabled()) {
        LOG.debug("Added priority=" + priority);
      }
    }
    TreeMap<Resource, ResourceRequestInfo> reqMap = 
                                          remoteRequests.get(resourceName);
    if (reqMap == null) {
      // capabilities are stored in reverse sorted order. smallest last.
      reqMap = new TreeMap<Resource, ResourceRequestInfo>(
          new ResourceReverseMemoryThenCpuComparator());
      remoteRequests.put(resourceName, reqMap);
    }
    ResourceRequestInfo resourceRequestInfo = reqMap.get(capability);
    if (resourceRequestInfo == null) {
      resourceRequestInfo =
          new ResourceRequestInfo(priority, resourceName, capability,
              relaxLocality);
      reqMap.put(capability, resourceRequestInfo);
    }
    
    resourceRequestInfo.remoteRequest.setNumContainers(
         resourceRequestInfo.remoteRequest.getNumContainers() + 1);

    if (relaxLocality) {
      resourceRequestInfo.containerRequests.add(req);
    }
    
    if (ResourceRequest.ANY.equals(resourceName)) {
      resourceRequestInfo.remoteRequest.setNodeLabelExpression(labelExpression);
    }

    // Note this down for next interaction with ResourceManager
    addResourceRequestToAsk(resourceRequestInfo.remoteRequest);

    if (LOG.isDebugEnabled()) {
      LOG.debug("addResourceRequest:" + " applicationId="
          + " priority=" + priority.getPriority()
          + " resourceName=" + resourceName + " numContainers="
          + resourceRequestInfo.remoteRequest.getNumContainers() 
          + " #asks=" + ask.size());
    }
  }

};