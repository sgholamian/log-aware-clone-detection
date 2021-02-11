//,temp,TestAMRMProxyService.java,458,512,temp,TestFederationInterceptor.java,174,217
//,3
public class xxx {
  private List<Container> getContainersAndAssert(int appId,
      int numberOfResourceRequests) throws Exception {
    AllocateRequest allocateRequest =
        Records.newRecord(AllocateRequest.class);
    allocateRequest.setResponseId(1);

    List<Container> containers =
        new ArrayList<Container>(numberOfResourceRequests);
    List<ResourceRequest> askList =
        new ArrayList<ResourceRequest>(numberOfResourceRequests);
    for (int testAppId = 0; testAppId < numberOfResourceRequests; testAppId++) {
      askList.add(createResourceRequest(
          "test-node-" + Integer.toString(testAppId), 6000, 2,
          testAppId % 5, 1));
    }

    allocateRequest.setAskList(askList);

    AllocateResponse allocateResponse = allocate(appId, allocateRequest);
    Assert.assertNotNull("allocate() returned null response",
        allocateResponse);
    Assert.assertNull(
        "new AMRMToken from RM should have been nulled by AMRMProxyService",
        allocateResponse.getAMRMToken());

    containers.addAll(allocateResponse.getAllocatedContainers());

    // Send max 10 heart beats to receive all the containers. If not, we will
    // fail the test
    int numHeartbeat = 0;
    while (containers.size() < askList.size() && numHeartbeat++ < 10) {
      allocateResponse =
          allocate(appId, Records.newRecord(AllocateRequest.class));
      Assert.assertNotNull("allocate() returned null response",
          allocateResponse);
      Assert.assertNull(
          "new AMRMToken from RM should have been nulled by AMRMProxyService",
          allocateResponse.getAMRMToken());

      containers.addAll(allocateResponse.getAllocatedContainers());

      LOG.info("Number of allocated containers in this request: "
          + Integer.toString(allocateResponse.getAllocatedContainers()
              .size()));
      LOG.info("Total number of allocated containers: "
          + Integer.toString(containers.size()));
      Thread.sleep(10);
    }

    // We broadcast the request, the number of containers we received will be
    // higher than we ask
    Assert.assertTrue("The asklist count is not same as response",
        askList.size() <= containers.size());
    return containers;
  }

};