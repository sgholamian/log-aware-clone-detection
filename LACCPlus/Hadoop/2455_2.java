//,temp,AMRMClientImpl.java,952,986,temp,AMRMClientImpl.java,924,950
//,3
public class xxx {
  private void addResourceRequest(Priority priority, String resourceName,
      ExecutionTypeRequest execTypeReq, Resource capability, T req,
      boolean relaxLocality, String labelExpression) {
    RemoteRequestsTable<T> remoteRequestsTable =
        getTable(req.getAllocationRequestId());
    if (remoteRequestsTable == null) {
      remoteRequestsTable = new RemoteRequestsTable<>();
      putTable(req.getAllocationRequestId(), remoteRequestsTable);
    }
    @SuppressWarnings("unchecked")
    ResourceRequestInfo resourceRequestInfo = remoteRequestsTable
        .addResourceRequest(req.getAllocationRequestId(), priority,
            resourceName, execTypeReq, capability, req, relaxLocality,
            labelExpression);

    // Note this down for next interaction with ResourceManager
    addResourceRequestToAsk(resourceRequestInfo.remoteRequest);

    if (LOG.isDebugEnabled()) {
      LOG.debug("Adding request to ask " + resourceRequestInfo.remoteRequest);
      LOG.debug("addResourceRequest:" + " applicationId="
          + " priority=" + priority.getPriority()
          + " resourceName=" + resourceName + " numContainers="
          + resourceRequestInfo.remoteRequest.getNumContainers() 
          + " #asks=" + ask.size());
    }
  }

};