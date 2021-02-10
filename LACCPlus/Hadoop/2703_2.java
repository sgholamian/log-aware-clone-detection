//,temp,LoadBalancingKMSClientProvider.java,483,493,temp,DomainPeerServer.java,79,86
//,3
public class xxx {
  @Override
  public void close() throws IOException {
    try {
      sock.close();
    } catch (IOException e) {
      LOG.error("error closing DomainPeerServer: ", e);
    }
  }

};