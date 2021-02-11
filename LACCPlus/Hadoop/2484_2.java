//,temp,TestRouterClientRMService.java,94,171,temp,TestRouterRMAdminService.java,95,180
//,3
public class xxx {
  @Test
  public void testRouterRMAdminServiceE2E() throws Exception {

    String user = "test1";

    LOG.info("testRouterRMAdminServiceE2E - Refresh Queues");

    RefreshQueuesResponse responseRefreshQueues = refreshQueues(user);
    Assert.assertNotNull(responseRefreshQueues);

    LOG.info("testRouterRMAdminServiceE2E - Refresh Nodes");

    RefreshNodesResponse responseRefreshNodes = refreshNodes(user);
    Assert.assertNotNull(responseRefreshNodes);

    LOG.info("testRouterRMAdminServiceE2E - Refresh Super User");

    RefreshSuperUserGroupsConfigurationResponse responseRefreshSuperUser =
        refreshSuperUserGroupsConfiguration(user);
    Assert.assertNotNull(responseRefreshSuperUser);

    LOG.info("testRouterRMAdminServiceE2E - Refresh User to Group");

    RefreshUserToGroupsMappingsResponse responseRefreshUserToGroup =
        refreshUserToGroupsMappings(user);
    Assert.assertNotNull(responseRefreshUserToGroup);

    LOG.info("testRouterRMAdminServiceE2E - Refresh Admin Acls");

    RefreshAdminAclsResponse responseRefreshAdminAcls = refreshAdminAcls(user);
    Assert.assertNotNull(responseRefreshAdminAcls);

    LOG.info("testRouterRMAdminServiceE2E - Refresh Service Acls");

    RefreshServiceAclsResponse responseRefreshServiceAcls =
        refreshServiceAcls(user);
    Assert.assertNotNull(responseRefreshServiceAcls);

    LOG.info("testRouterRMAdminServiceE2E - Update Node Resource");

    UpdateNodeResourceResponse responseUpdateNodeResource =
        updateNodeResource(user);
    Assert.assertNotNull(responseUpdateNodeResource);

    LOG.info("testRouterRMAdminServiceE2E - Refresh Nodes Resource");

    RefreshNodesResourcesResponse responseRefreshNodesResources =
        refreshNodesResources(user);
    Assert.assertNotNull(responseRefreshNodesResources);

    LOG.info("testRouterRMAdminServiceE2E - Add To Cluster NodeLabels");

    AddToClusterNodeLabelsResponse responseAddToClusterNodeLabels =
        addToClusterNodeLabels(user);
    Assert.assertNotNull(responseAddToClusterNodeLabels);

    LOG.info("testRouterRMAdminServiceE2E - Remove To Cluster NodeLabels");

    RemoveFromClusterNodeLabelsResponse responseRemoveFromClusterNodeLabels =
        removeFromClusterNodeLabels(user);
    Assert.assertNotNull(responseRemoveFromClusterNodeLabels);

    LOG.info("testRouterRMAdminServiceE2E - Replace Labels On Node");

    ReplaceLabelsOnNodeResponse responseReplaceLabelsOnNode =
        replaceLabelsOnNode(user);
    Assert.assertNotNull(responseReplaceLabelsOnNode);

    LOG.info("testRouterRMAdminServiceE2E - Check For Decommissioning Nodes");

    CheckForDecommissioningNodesResponse responseCheckForDecom =
        checkForDecommissioningNodes(user);
    Assert.assertNotNull(responseCheckForDecom);

    LOG.info("testRouterRMAdminServiceE2E - Refresh Cluster Max Priority");

    RefreshClusterMaxPriorityResponse responseRefreshClusterMaxPriority =
        refreshClusterMaxPriority(user);
    Assert.assertNotNull(responseRefreshClusterMaxPriority);

    LOG.info("testRouterRMAdminServiceE2E - Get Groups For User");

    String[] responseGetGroupsForUser = getGroupsForUser(user);
    Assert.assertNotNull(responseGetGroupsForUser);

  }

};