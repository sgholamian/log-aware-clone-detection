//,temp,XceiverClientGrpc.java,205,216,temp,XceiverClient.java,105,115
//,3
public class xxx {
  private void reconnect() throws IOException {
    try {
      connect();
    } catch (Exception e) {
      LOG.error("Error while connecting: ", e);
      throw new IOException(e);
    }

    if (channel == null || !isConnected()) {
      throw new IOException("This channel is not connected.");
    }
  }

};