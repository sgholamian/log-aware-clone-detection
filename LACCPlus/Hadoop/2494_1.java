//,temp,TestAMRMProxyService.java,514,570,temp,TestFederationInterceptor.java,174,217
//,3
public class xxx {
  private void releaseContainersAndAssert(int appId,
      List<Container> containers) throws Exception {
    Assert.assertTrue(containers.size() > 0);
    AllocateRequest allocateRequest =
        Records.newRecord(AllocateRequest.class);
    allocateRequest.setResponseId(1);

    List<ContainerId> relList =
        new ArrayList<ContainerId>(containers.size());
    for (Container container : containers) {
      relList.add(container.getId());
    }

    allocateRequest.setReleaseList(relList);

    AllocateResponse allocateResponse = allocate(appId, allocateRequest);
    Assert.assertNotNull(allocateResponse);
    Assert.assertNull(
        "new AMRMToken from RM should have been nulled by AMRMProxyService",
        allocateResponse.getAMRMToken());

    // We need to make sure all the resource managers received the
    // release list. The containers sent by the mock resource managers will be
    // aggregated and returned back to us and we can assert if all the release
    // lists reached the sub-clusters
    List<ContainerId> containersForReleasedContainerIds = new ArrayList<>();
    List<ContainerId> newlyFinished = getCompletedContainerIds(
        allocateResponse.getCompletedContainersStatuses());
    containersForReleasedContainerIds.addAll(newlyFinished);

    // Send max 10 heart beats to receive all the containers. If not, we will
    // fail the test
    int numHeartbeat = 0;
    while (containersForReleasedContainerIds.size() < relList.size()
        && numHeartbeat++ < 10) {
      allocateResponse =
          allocate(appId, Records.newRecord(AllocateRequest.class));
      Assert.assertNotNull(allocateResponse);
      Assert.assertNull(
          "new AMRMToken from RM should have been nulled by AMRMProxyService",
          allocateResponse.getAMRMToken());

      newlyFinished = getCompletedContainerIds(
          allocateResponse.getCompletedContainersStatuses());
      containersForReleasedContainerIds.addAll(newlyFinished);

      LOG.info("Number of containers received in this request: "
          + Integer.toString(allocateResponse.getAllocatedContainers()
              .size()));
      LOG.info("Total number of containers received: "
          + Integer.toString(containersForReleasedContainerIds.size()));
      Thread.sleep(10);
    }

    Assert.assertEquals(relList.size(),
        containersForReleasedContainerIds.size());
  }

};