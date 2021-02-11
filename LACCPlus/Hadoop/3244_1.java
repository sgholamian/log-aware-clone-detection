//,temp,MRAMSimulator.java,285,357,temp,StreamAMSimulator.java,201,247
//,3
public class xxx {
  @Override
  protected void sendContainerRequest()
          throws YarnException, IOException, InterruptedException {
    if (isFinished) {
      return;
    }

    // send out request
    List<ResourceRequest> ask = null;
    if (mapFinished != mapTotal) {
      // map phase
      if (!pendingMaps.isEmpty()) {
        ask = packageRequests(mergeLists(pendingMaps, scheduledMaps),
            PRIORITY_MAP);
        LOG.debug("Application {} sends out request for {} mappers.",
            appId, pendingMaps.size());
        scheduledMaps.addAll(pendingMaps);
        pendingMaps.clear();
      } else if (!pendingFailedMaps.isEmpty()) {
        ask = packageRequests(mergeLists(pendingFailedMaps, scheduledMaps),
            PRIORITY_MAP);
        LOG.debug("Application {} sends out requests for {} failed mappers.",
            appId, pendingFailedMaps.size());
        scheduledMaps.addAll(pendingFailedMaps);
        pendingFailedMaps.clear();
      }
    } else if (reduceFinished != reduceTotal) {
      // reduce phase
      if (!pendingReduces.isEmpty()) {
        ask = packageRequests(mergeLists(pendingReduces, scheduledReduces),
            PRIORITY_REDUCE);
        LOG.debug("Application {} sends out requests for {} reducers.",
                appId, pendingReduces.size());
        scheduledReduces.addAll(pendingReduces);
        pendingReduces.clear();
      } else if (!pendingFailedReduces.isEmpty()) {
        ask = packageRequests(mergeLists(pendingFailedReduces, scheduledReduces),
            PRIORITY_REDUCE);
        LOG.debug("Application {} sends out request for {} failed reducers.",
            appId, pendingFailedReduces.size());
        scheduledReduces.addAll(pendingFailedReduces);
        pendingFailedReduces.clear();
      }
    }

    if (ask == null) {
      ask = new ArrayList<>();
    }

    final AllocateRequest request = createAllocateRequest(ask);
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