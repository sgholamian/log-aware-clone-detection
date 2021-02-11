//,temp,AbstractTestIPC.java,367,390,temp,AbstractTestIPC.java,256,274
//,3
public class xxx {
  @Test
  public void testRemoteError() throws IOException, ServiceException {
    RpcServer rpcServer = createRpcServer(null, "testRpcServer",
        Lists.newArrayList(new RpcServer.BlockingServiceAndInterface(
            SERVICE, null)), new InetSocketAddress("localhost", 0), CONF,
        new FifoRpcScheduler(CONF, 1));
    try (AbstractRpcClient<?> client = createRpcClient(CONF)) {
      rpcServer.start();
      BlockingInterface stub = newBlockingStub(client, rpcServer.getListenerAddress());
      stub.error(null, EmptyRequestProto.getDefaultInstance());
    } catch (ServiceException e) {
      LOG.info("Caught expected exception: " + e);
      IOException ioe = ProtobufUtil.handleRemoteException(e);
      assertTrue(ioe instanceof DoNotRetryIOException);
      assertTrue(ioe.getMessage().contains("server error!"));
    } finally {
      rpcServer.stop();
    }
  }

};