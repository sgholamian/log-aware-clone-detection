//,temp,TestContainerLaunchRPC.java,90,146,temp,TestContainerResourceIncreaseRPC.java,83,132
//,3
public class xxx {
  private void testRPCTimeout(String rpcClass) throws Exception {
    Configuration conf = new Configuration();
    // set timeout low for the test
    conf.setInt("yarn.rpc.nm-command-timeout", 3000);
    conf.set(YarnConfiguration.IPC_RPC_IMPL, rpcClass);
    YarnRPC rpc = YarnRPC.create(conf);
    String bindAddr = "localhost:0";
    InetSocketAddress addr = NetUtils.createSocketAddr(bindAddr);
    Server server = rpc.getServer(ContainerManagementProtocol.class,
        new DummyContainerManager(), addr, conf, null, 1);
    server.start();
    try {
      ContainerManagementProtocol proxy =
          (ContainerManagementProtocol) rpc.getProxy(
              ContainerManagementProtocol.class,
                  server.getListenerAddress(), conf);
      ApplicationId applicationId = ApplicationId.newInstance(0, 0);
      ApplicationAttemptId applicationAttemptId =
          ApplicationAttemptId.newInstance(applicationId, 0);
      ContainerId containerId =
          ContainerId.newContainerId(applicationAttemptId, 100);
      NodeId nodeId = NodeId.newInstance("localhost", 1234);
      Resource resource = Resource.newInstance(1234, 2);
      ContainerTokenIdentifier containerTokenIdentifier =
          new ContainerTokenIdentifier(containerId, "localhost", "user",
              resource, System.currentTimeMillis() + 10000, 42, 42,
                  Priority.newInstance(0), 0);
      Token containerToken =
          newContainerToken(nodeId, "password".getBytes(),
              containerTokenIdentifier);
      // Construct container resource increase request,
      List<Token> increaseTokens = new ArrayList<>();
      increaseTokens.add(containerToken);
      ContainerUpdateRequest request = ContainerUpdateRequest
          .newInstance(increaseTokens);

      try {
        proxy.updateContainer(request);
      } catch (Exception e) {
        LOG.info(StringUtils.stringifyException(e));
        Assert.assertEquals("Error, exception is not: "
            + SocketTimeoutException.class.getName(),
            SocketTimeoutException.class.getName(), e.getClass().getName());
        return;
      }
    } finally {
      server.stop();
    }
    Assert.fail("timeout exception should have occurred!");
  }

};