//,temp,LlapTaskCommunicator.java,956,963,temp,LlapTaskUmbilicalExternalClient.java,515,524
//,3
public class xxx {
    @Override
    public void nodeHeartbeat(Text hostname, Text uniqueId, int port, TezAttemptArray aw,
        BooleanArray guaranteed) throws IOException {
      if (LOG.isDebugEnabled()) {
        LOG.debug("Node heartbeat from " + hostname + ":" + port + ", " + uniqueId);
      }
      // External client currently cannot use guaranteed.
      updateHeartbeatInfo(hostname.toString(), uniqueId.toString(), port, aw);
      // No need to propagate to this to the responder
    }

};