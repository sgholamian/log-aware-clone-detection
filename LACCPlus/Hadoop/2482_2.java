//,temp,TestAMAuthorization.java,313,380,temp,TestAMAuthorization.java,251,311
//,3
public class xxx {
  @Test
  public void testAuthorizedAccess() throws Exception {
    MyContainerManager containerManager = new MyContainerManager();
    rm =
        new MockRMWithAMS(conf, containerManager);
    rm.start();

    MockNM nm1 = rm.registerNode("localhost:1234", 5120);

    Map<ApplicationAccessType, String> acls =
        new HashMap<ApplicationAccessType, String>(2);
    acls.put(ApplicationAccessType.VIEW_APP, "*");
    RMApp app = rm.submitApp(1024, "appname", "appuser", acls);

    nm1.nodeHeartbeat(true);

    int waitCount = 0;
    while (containerManager.containerTokens == null && waitCount++ < 20) {
      LOG.info("Waiting for AM Launch to happen..");
      Thread.sleep(1000);
    }
    Assert.assertNotNull(containerManager.containerTokens);

    RMAppAttempt attempt = app.getCurrentAppAttempt();
    ApplicationAttemptId applicationAttemptId = attempt.getAppAttemptId();
    waitForLaunchedState(attempt);

    // Create a client to the RM.
    final Configuration conf = rm.getConfig();
    final YarnRPC rpc = YarnRPC.create(conf);

    UserGroupInformation currentUser = UserGroupInformation
        .createRemoteUser(applicationAttemptId.toString());
    Credentials credentials = containerManager.getContainerCredentials();
    final InetSocketAddress rmBindAddress =
        rm.getApplicationMasterService().getBindAddress();
    Token<? extends TokenIdentifier> amRMToken =
        MockRMWithAMS.setupAndReturnAMRMToken(rmBindAddress,
          credentials.getAllTokens());
    currentUser.addToken(amRMToken);
    ApplicationMasterProtocol client = currentUser
        .doAs(new PrivilegedAction<ApplicationMasterProtocol>() {
          @Override
          public ApplicationMasterProtocol run() {
            return (ApplicationMasterProtocol) rpc.getProxy(ApplicationMasterProtocol.class, rm
              .getApplicationMasterService().getBindAddress(), conf);
          }
        });

    RegisterApplicationMasterRequest request = Records
        .newRecord(RegisterApplicationMasterRequest.class);
    RegisterApplicationMasterResponse response =
        client.registerApplicationMaster(request);
    Assert.assertNotNull(response.getClientToAMTokenMasterKey());
    if (UserGroupInformation.isSecurityEnabled()) {
      Assert
        .assertTrue(response.getClientToAMTokenMasterKey().array().length > 0);
    }
    Assert.assertEquals("Register response has bad ACLs", "*",
        response.getApplicationACLs().get(ApplicationAccessType.VIEW_APP));
  }

};