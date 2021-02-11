//,temp,TestIPC.java,606,624,temp,TestIPC.java,584,604
//,3
public class xxx {
  @Test(timeout=60000)
  public void testIpcConnectTimeout() throws IOException {
    // start server
    Server server = new TestServer(1, true);
    InetSocketAddress addr = NetUtils.getConnectAddress(server);
    //Intentionally do not start server to get a connection timeout

    // start client
    Client.setConnectTimeout(conf, 100);
    Client client = new Client(LongWritable.class, conf);
    // set the rpc timeout to twice the MIN_SLEEP_TIME
    try {
      client.call(new LongWritable(RANDOM.nextLong()),
              addr, null, null, MIN_SLEEP_TIME*2, conf);
      fail("Expected an exception to have been thrown");
    } catch (SocketTimeoutException e) {
      LOG.info("Get a SocketTimeoutException ", e);
    }
  }

};