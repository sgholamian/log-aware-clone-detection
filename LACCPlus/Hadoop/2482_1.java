//,temp,TestAMAuthorization.java,313,380,temp,TestAMAuthorization.java,251,311
//,3
public class xxx {
  @Test
  public void testUnauthorizedAccess() throws Exception {
    MyContainerManager containerManager = new MyContainerManager();
    rm = new MockRMWithAMS(conf, containerManager);
    rm.start();

    MockNM nm1 = rm.registerNode("localhost:1234", 5120);

    RMApp app = rm.submitApp(1024);

    nm1.nodeHeartbeat(true);

    int waitCount = 0;
    while (containerManager.containerTokens == null && waitCount++ < 40) {
      LOG.info("Waiting for AM Launch to happen..");
      Thread.sleep(1000);
    }
    Assert.assertNotNull(containerManager.containerTokens);

    RMAppAttempt attempt = app.getCurrentAppAttempt();
    ApplicationAttemptId applicationAttemptId = attempt.getAppAttemptId();
    waitForLaunchedState(attempt);

    final Configuration conf = rm.getConfig();
    final YarnRPC rpc = YarnRPC.create(conf);
    final InetSocketAddress serviceAddr = conf.getSocketAddr(
        YarnConfiguration.RM_SCHEDULER_ADDRESS,
        YarnConfiguration.DEFAULT_RM_SCHEDULER_ADDRESS,
        YarnConfiguration.DEFAULT_RM_SCHEDULER_PORT);

    UserGroupInformation currentUser = UserGroupInformation
        .createRemoteUser(applicationAttemptId.toString());

    // First try contacting NM without tokens
    ApplicationMasterProtocol client = currentUser
        .doAs(new PrivilegedAction<ApplicationMasterProtocol>() {
          @Override
          public ApplicationMasterProtocol run() {
            return (ApplicationMasterProtocol) rpc.getProxy(ApplicationMasterProtocol.class,
                serviceAddr, conf);
          }
        });
    
    RegisterApplicationMasterRequest request = Records
        .newRecord(RegisterApplicationMasterRequest.class);
    try {
      client.registerApplicationMaster(request);
      Assert.fail("Should fail with authorization error");
    } catch (Exception e) {
      if (isCause(AccessControlException.class, e)) {
        // Because there are no tokens, the request should be rejected as the
        // server side will assume we are trying simple auth.
        String expectedMessage = "";
        if (UserGroupInformation.isSecurityEnabled()) {
          expectedMessage = "Client cannot authenticate via:[TOKEN]";
        } else {
          expectedMessage =
              "SIMPLE authentication is not enabled.  Available:[TOKEN]";
        }
        Assert.assertTrue(e.getCause().getMessage().contains(expectedMessage)); 
      } else {
        throw e;
      }
    }

    // TODO: Add validation of invalid authorization when there's more data in
    // the AMRMToken
  }

};