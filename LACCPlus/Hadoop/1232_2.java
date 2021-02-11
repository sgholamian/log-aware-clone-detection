//,temp,TestIPC.java,606,624,temp,TestIPC.java,584,604
//,3
public class xxx {
  @Test(timeout=60000)
  public void testIpcTimeout() throws IOException {
    // start server
    Server server = new TestServer(1, true);
    InetSocketAddress addr = NetUtils.getConnectAddress(server);
    server.start();

    // start client
    Client client = new Client(LongWritable.class, conf);
    // set timeout to be less than MIN_SLEEP_TIME
    try {
      client.call(new LongWritable(RANDOM.nextLong()),
              addr, null, null, MIN_SLEEP_TIME/2, conf);
      fail("Expected an exception to have been thrown");
    } catch (SocketTimeoutException e) {
      LOG.info("Get a SocketTimeoutException ", e);
    }
    // set timeout to be bigger than 3*ping interval
    client.call(new LongWritable(RANDOM.nextLong()),
        addr, null, null, 3*PING_INTERVAL+MIN_SLEEP_TIME, conf);
  }

};