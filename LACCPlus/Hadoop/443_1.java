//,temp,MRAMSimulator.java,157,185,temp,AMSimulator.java,288,314
//,3
public class xxx {
  protected void requestAMContainer()
          throws YarnException, IOException, InterruptedException {
    List<ResourceRequest> ask = new ArrayList<ResourceRequest>();
    ResourceRequest amRequest = createResourceRequest(
            BuilderUtils.newResource(MR_AM_CONTAINER_RESOURCE_MEMORY_MB,
                    MR_AM_CONTAINER_RESOURCE_VCORES),
            ResourceRequest.ANY, 1, 1);
    ask.add(amRequest);
    LOG.debug(MessageFormat.format("Application {0} sends out allocate " +
            "request for its AM", appId));
    final AllocateRequest request = this.createAllocateRequest(ask);

    UserGroupInformation ugi =
            UserGroupInformation.createRemoteUser(appAttemptId.toString());
    Token<AMRMTokenIdentifier> token = rm.getRMContext().getRMApps()
            .get(appAttemptId.getApplicationId())
            .getRMAppAttempt(appAttemptId).getAMRMToken();
    ugi.addTokenIdentifier(token.decodeIdentifier());
    AllocateResponse response = ugi.doAs(
            new PrivilegedExceptionAction<AllocateResponse>() {
      @Override
      public AllocateResponse run() throws Exception {
        return rm.getApplicationMasterService().allocate(request);
      }
    });
    if (response != null) {
      responseQueue.put(response);
    }
  }

};