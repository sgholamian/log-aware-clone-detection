//,temp,TestAMRMProxyService.java,458,512,temp,TestFederationInterceptor.java,174,217
//,3
public class xxx {
  private List<Container> getContainersAndAssert(int numberOfResourceRequests,
      int numberOfAllocationExcepted) throws Exception {
    AllocateRequest allocateRequest = Records.newRecord(AllocateRequest.class);
    allocateRequest.setResponseId(1);

    List<Container> containers =
        new ArrayList<Container>(numberOfResourceRequests);
    List<ResourceRequest> askList =
        new ArrayList<ResourceRequest>(numberOfResourceRequests);
    for (int id = 0; id < numberOfResourceRequests; id++) {
      askList.add(createResourceRequest("test-node-" + Integer.toString(id),
          6000, 2, id % 5, 1));
    }

    allocateRequest.setAskList(askList);

    AllocateResponse allocateResponse = interceptor.allocate(allocateRequest);
    Assert.assertNotNull("allocate() returned null response", allocateResponse);

    containers.addAll(allocateResponse.getAllocatedContainers());
    LOG.info("Number of allocated containers in the original request: "
        + Integer.toString(allocateResponse.getAllocatedContainers().size()));

    // Send max 10 heart beats to receive all the containers. If not, we will
    // fail the test
    int numHeartbeat = 0;
    while (containers.size() < numberOfAllocationExcepted
        && numHeartbeat++ < 10) {
      allocateResponse =
          interceptor.allocate(Records.newRecord(AllocateRequest.class));
      Assert.assertNotNull("allocate() returned null response",
          allocateResponse);

      containers.addAll(allocateResponse.getAllocatedContainers());

      LOG.info("Number of allocated containers in this request: "
          + Integer.toString(allocateResponse.getAllocatedContainers().size()));
      LOG.info("Total number of allocated containers: "
          + Integer.toString(containers.size()));
      Thread.sleep(10);
    }
    Assert.assertEquals(numberOfAllocationExcepted, containers.size());
    return containers;
  }

};