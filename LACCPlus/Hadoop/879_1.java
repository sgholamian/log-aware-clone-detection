//,temp,ProtocolHATestBase.java,224,240,temp,TestRMFailover.java,91,108
//,3
public class xxx {
  protected void verifyClientConnection() {
    int numRetries = 3;
    while(numRetries-- > 0) {
      Configuration conf = new YarnConfiguration(this.conf);
      YarnClient client = createAndStartYarnClient(conf);
      try {
        Thread.sleep(100);
        client.getApplications();
        return;
      } catch (Exception e) {
        LOG.error(e.getMessage());
      } finally {
        client.stop();
      }
    }
    fail("Client couldn't connect to the Active RM");
  }

};