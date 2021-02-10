//,temp,AMRMClientImpl.java,639,690,temp,RMContainerRequestor.java,426,462
//,3
public class xxx {
  private void addResourceRequest(Priority priority, String resourceName,
      Resource capability, String nodeLabelExpression) {
    Map<String, Map<Resource, ResourceRequest>> remoteRequests =
      this.remoteRequestsTable.get(priority);
    if (remoteRequests == null) {
      remoteRequests = new HashMap<String, Map<Resource, ResourceRequest>>();
      this.remoteRequestsTable.put(priority, remoteRequests);
      if (LOG.isDebugEnabled()) {
        LOG.debug("Added priority=" + priority);
      }
    }
    Map<Resource, ResourceRequest> reqMap = remoteRequests.get(resourceName);
    if (reqMap == null) {
      reqMap = new HashMap<Resource, ResourceRequest>();
      remoteRequests.put(resourceName, reqMap);
    }
    ResourceRequest remoteRequest = reqMap.get(capability);
    if (remoteRequest == null) {
      remoteRequest = recordFactory.newRecordInstance(ResourceRequest.class);
      remoteRequest.setPriority(priority);
      remoteRequest.setResourceName(resourceName);
      remoteRequest.setCapability(capability);
      remoteRequest.setNumContainers(0);
      remoteRequest.setNodeLabelExpression(nodeLabelExpression);
      reqMap.put(capability, remoteRequest);
    }
    remoteRequest.setNumContainers(remoteRequest.getNumContainers() + 1);

    // Note this down for next interaction with ResourceManager
    addResourceRequestToAsk(remoteRequest);
    if (LOG.isDebugEnabled()) {
      LOG.debug("addResourceRequest:" + " applicationId="
          + applicationId.getId() + " priority=" + priority.getPriority()
          + " resourceName=" + resourceName + " numContainers="
          + remoteRequest.getNumContainers() + " #asks=" + ask.size());
    }
  }

};