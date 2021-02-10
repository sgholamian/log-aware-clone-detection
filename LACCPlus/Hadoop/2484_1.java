//,temp,TestRouterClientRMService.java,94,171,temp,TestRouterRMAdminService.java,95,180
//,3
public class xxx {
  @Test
  public void testRouterClientRMServiceE2E() throws Exception {

    String user = "test1";

    LOG.info("testRouterClientRMServiceE2E - Get New Application");

    GetNewApplicationResponse responseGetNewApp = getNewApplication(user);
    Assert.assertNotNull(responseGetNewApp);

    LOG.info("testRouterClientRMServiceE2E - Submit Application");

    SubmitApplicationResponse responseSubmitApp =
        submitApplication(responseGetNewApp.getApplicationId(), user);
    Assert.assertNotNull(responseSubmitApp);

    LOG.info("testRouterClientRMServiceE2E - Kill Application");

    KillApplicationResponse responseKillApp =
        forceKillApplication(responseGetNewApp.getApplicationId(), user);
    Assert.assertNotNull(responseKillApp);

    LOG.info("testRouterClientRMServiceE2E - Get Cluster Metrics");

    GetClusterMetricsResponse responseGetClusterMetrics =
        getClusterMetrics(user);
    Assert.assertNotNull(responseGetClusterMetrics);

    LOG.info("testRouterClientRMServiceE2E - Get Cluster Nodes");

    GetClusterNodesResponse responseGetClusterNodes = getClusterNodes(user);
    Assert.assertNotNull(responseGetClusterNodes);

    LOG.info("testRouterClientRMServiceE2E - Get Queue Info");

    GetQueueInfoResponse responseGetQueueInfo = getQueueInfo(user);
    Assert.assertNotNull(responseGetQueueInfo);

    LOG.info("testRouterClientRMServiceE2E - Get Queue User");

    GetQueueUserAclsInfoResponse responseGetQueueUser = getQueueUserAcls(user);
    Assert.assertNotNull(responseGetQueueUser);

    LOG.info("testRouterClientRMServiceE2E - Get Cluster Node");

    GetClusterNodeLabelsResponse responseGetClusterNode =
        getClusterNodeLabels(user);
    Assert.assertNotNull(responseGetClusterNode);

    LOG.info("testRouterClientRMServiceE2E - Move Application Across Queues");

    MoveApplicationAcrossQueuesResponse responseMoveApp =
        moveApplicationAcrossQueues(user, responseGetNewApp.getApplicationId());
    Assert.assertNotNull(responseMoveApp);

    LOG.info("testRouterClientRMServiceE2E - Get New Reservation");

    GetNewReservationResponse getNewReservationResponse =
        getNewReservation(user);

    LOG.info("testRouterClientRMServiceE2E - Submit Reservation");

    ReservationSubmissionResponse responseSubmitReser =
        submitReservation(user, getNewReservationResponse.getReservationId());
    Assert.assertNotNull(responseSubmitReser);

    LOG.info("testRouterClientRMServiceE2E - Update Reservation");

    ReservationUpdateResponse responseUpdateReser =
        updateReservation(user, getNewReservationResponse.getReservationId());
    Assert.assertNotNull(responseUpdateReser);

    LOG.info("testRouterClientRMServiceE2E - Delete Reservation");

    ReservationDeleteResponse responseDeleteReser =
        deleteReservation(user, getNewReservationResponse.getReservationId());
    Assert.assertNotNull(responseDeleteReser);
  }

};