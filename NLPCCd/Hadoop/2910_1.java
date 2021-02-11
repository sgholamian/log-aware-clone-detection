//,temp,sample_3223.java,2,16,temp,sample_3230.java,2,16
//,2
public class xxx {
public void dummy_method(){
RefreshQueuesResponse responseRefreshQueues = refreshQueues(user);
Assert.assertNotNull(responseRefreshQueues);
RefreshNodesResponse responseRefreshNodes = refreshNodes(user);
Assert.assertNotNull(responseRefreshNodes);
RefreshSuperUserGroupsConfigurationResponse responseRefreshSuperUser = refreshSuperUserGroupsConfiguration(user);
Assert.assertNotNull(responseRefreshSuperUser);
RefreshUserToGroupsMappingsResponse responseRefreshUserToGroup = refreshUserToGroupsMappings(user);
Assert.assertNotNull(responseRefreshUserToGroup);
RefreshAdminAclsResponse responseRefreshAdminAcls = refreshAdminAcls(user);
Assert.assertNotNull(responseRefreshAdminAcls);


log.info("refresh service acls");
}

};