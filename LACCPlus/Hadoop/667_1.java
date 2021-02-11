//,temp,RemoteBlockReader.java,470,480,temp,RemoteBlockReader2.java,336,346
//,3
public class xxx {
  void sendReadResult(Peer peer, Status statusCode) {
    assert !sentStatusCode : "already sent status code to " + peer;
    try {
      RemoteBlockReader2.writeReadResult(peer.getOutputStream(), statusCode);
      sentStatusCode = true;
    } catch (IOException e) {
      // It's ok not to be able to send this. But something is probably wrong.
      LOG.info("Could not send read status (" + statusCode + ") to datanode " +
               peer.getRemoteAddressString() + ": " + e.getMessage());
    }
  }

};