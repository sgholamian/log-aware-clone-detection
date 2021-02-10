//,temp,MRAMSimulator.java,285,357,temp,StreamAMSimulator.java,201,247
//,3
public class xxx {
  @Override
  protected void sendContainerRequest()
          throws YarnException, IOException, InterruptedException {

    // send out request
    List<ResourceRequest> ask = new ArrayList<>();
    List<ContainerId> release = new ArrayList<>();
    if (!isFinished) {
      if (!pendingStreams.isEmpty()) {
        ask = packageRequests(mergeLists(pendingStreams, scheduledStreams),
            PRIORITY_MAP);
        LOG.debug("Application {} sends out request for {} streams.",
            appId, pendingStreams.size());
        scheduledStreams.addAll(pendingStreams);
        pendingStreams.clear();
      }
    }

    if(isFinished){
      release.addAll(assignedStreams.keySet());
      ask.clear();
    }

    final AllocateRequest request = createAllocateRequest(ask, release);
    if (totalContainers == 0) {
      request.setProgress(1.0f);
    } else {
      request.setProgress((float) finishedContainers / totalContainers);
    }

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