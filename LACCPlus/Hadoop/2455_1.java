//,temp,AMRMClientImpl.java,952,986,temp,AMRMClientImpl.java,924,950
//,3
public class xxx {
  private void decResourceRequest(Priority priority, String resourceName,
      ExecutionTypeRequest execTypeReq, Resource capability, T req) {
    RemoteRequestsTable<T> remoteRequestsTable =
        getTable(req.getAllocationRequestId());
    if (remoteRequestsTable != null) {
      @SuppressWarnings("unchecked")
      ResourceRequestInfo resourceRequestInfo =
          remoteRequestsTable.decResourceRequest(priority, resourceName,
              execTypeReq, capability, req);
      // send the ResourceRequest to RM even if is 0 because it needs to
      // override a previously sent value. If ResourceRequest was not sent
      // previously then sending 0 aught to be a no-op on RM
      if (resourceRequestInfo != null) {
        addResourceRequestToAsk(resourceRequestInfo.remoteRequest);

        // delete entry from map if no longer needed
        if (resourceRequestInfo.remoteRequest.getNumContainers() == 0) {
          remoteRequestsTable.remove(priority, resourceName,
              execTypeReq.getExecutionType(), capability);
        }

        if (LOG.isDebugEnabled()) {
          LOG.debug("AFTER decResourceRequest:"
              + " allocationRequestId=" + req.getAllocationRequestId()
              + " priority=" + priority.getPriority()
              + " resourceName=" + resourceName + " numContainers="
              + resourceRequestInfo.remoteRequest.getNumContainers()
              + " #asks=" + ask.size());
        }
      }
    } else {
      LOG.info("No remoteRequestTable found with allocationRequestId="
          + req.getAllocationRequestId());
    }
  }

};