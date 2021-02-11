//,temp,TestDelegationTokenRenewer.java,650,709,temp,TestDelegationTokenRenewer.java,570,635
//,3
public class xxx {
  @Test(timeout=60000)
  public void testDTKeepAlive1 () throws Exception {
    Configuration lconf = new Configuration(conf);
    lconf.setBoolean(YarnConfiguration.LOG_AGGREGATION_ENABLED, true);
    //Keep tokens alive for 6 seconds.
    lconf.setLong(YarnConfiguration.RM_NM_EXPIRY_INTERVAL_MS, 6000l);
    //Try removing tokens every second.
    lconf.setLong(
        YarnConfiguration.RM_DELAYED_DELEGATION_TOKEN_REMOVAL_INTERVAL_MS,
        1000l);
    DelegationTokenRenewer localDtr =
        createNewDelegationTokenRenewer(lconf, counter);
    RMContext mockContext = mock(RMContext.class);
    when(mockContext.getSystemCredentialsForApps()).thenReturn(
      new ConcurrentHashMap<ApplicationId, ByteBuffer>());
    ClientRMService mockClientRMService = mock(ClientRMService.class);
    when(mockContext.getClientRMService()).thenReturn(mockClientRMService);
    when(mockContext.getDelegationTokenRenewer()).thenReturn(
        localDtr);
    when(mockContext.getDispatcher()).thenReturn(dispatcher);
    InetSocketAddress sockAddr =
        InetSocketAddress.createUnresolved("localhost", 1234);
    when(mockClientRMService.getBindAddress()).thenReturn(sockAddr);
    localDtr.setRMContext(mockContext);
    localDtr.init(lconf);
    localDtr.start();
    
    MyFS dfs = (MyFS)FileSystem.get(lconf);
    LOG.info("dfs="+(Object)dfs.hashCode() + ";conf="+lconf.hashCode());
    
    Credentials ts = new Credentials();
    // get the delegation tokens
    MyToken token1 = dfs.getDelegationToken("user1");

    String nn1 = DelegationTokenRenewer.SCHEME + "://host1:0";
    ts.addToken(new Text(nn1), token1);

    // register the tokens for renewal
    ApplicationId applicationId_0 =  BuilderUtils.newApplicationId(0, 0);
    localDtr.addApplicationAsync(applicationId_0, ts, true, "user",
        new Configuration());
    waitForEventsToGetProcessed(localDtr);
    if (!eventQueue.isEmpty()){
      Event evt = eventQueue.take();
      if (evt instanceof RMAppEvent) {
        Assert.assertEquals(((RMAppEvent)evt).getType(), RMAppEventType.START);
      } else {
        fail("RMAppEvent.START was expected!!");
      }
    }
    
    localDtr.applicationFinished(applicationId_0);
    waitForEventsToGetProcessed(localDtr);

    //Token should still be around. Renewal should not fail.
    token1.renew(lconf);

    //Allow the keepalive time to run out
    Thread.sleep(10000l);

    //The token should have been cancelled at this point. Renewal will fail.
    try {
      token1.renew(lconf);
      fail("Renewal of cancelled token should have failed");
    } catch (InvalidToken ite) {}
  }

};