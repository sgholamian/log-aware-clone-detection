//,temp,ProtocolHATestBase.java,224,240,temp,TestRMFailover.java,91,108
//,3
public class xxx {
  private void verifyClientConnection() {
    int numRetries = 3;
    while(numRetries-- > 0) {
      Configuration conf = new YarnConfiguration(this.conf);
      YarnClient client = YarnClient.createYarnClient();
      client.init(conf);
      client.start();
      try {
        client.getApplications();
        return;
      } catch (Exception e) {
        LOG.error(e);
      } finally {
        client.stop();
      }
    }
    fail("Client couldn't connect to the Active RM");
  }

};