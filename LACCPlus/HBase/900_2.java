//,temp,AbstractTestIPC.java,256,274,temp,AbstractTestIPC.java,157,175
//,3
public class xxx {
  @Test
  public void testRTEDuringConnectionSetup() throws Exception {
    Configuration conf = HBaseConfiguration.create();
    RpcServer rpcServer = createRpcServer(null, "testRpcServer",
        Lists.newArrayList(new RpcServer.BlockingServiceAndInterface(
            SERVICE, null)), new InetSocketAddress("localhost", 0), CONF,
        new FifoRpcScheduler(CONF, 1));
    try (AbstractRpcClient<?> client = createRpcClientRTEDuringConnectionSetup(conf)) {
      rpcServer.start();
      BlockingInterface stub = newBlockingStub(client, rpcServer.getListenerAddress());
      stub.ping(null, EmptyRequestProto.getDefaultInstance());
      fail("Expected an exception to have been thrown!");
    } catch (Exception e) {
      LOG.info("Caught expected exception: " + e.toString());
      assertTrue(e.toString(), StringUtils.stringifyException(e).contains("Injected fault"));
    } finally {
      rpcServer.stop();
    }
  }

};