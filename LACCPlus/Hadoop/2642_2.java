//,temp,XceiverClientGrpc.java,205,216,temp,XceiverClient.java,105,115
//,3
public class xxx {
  public void reconnect() throws IOException {
    try {
      connect();
      if (channel == null || !channel.isActive()) {
        throw new IOException("This channel is not connected.");
      }
    } catch (Exception e) {
      LOG.error("Error while connecting: ", e);
      throw new IOException(e);
    }
  }

};