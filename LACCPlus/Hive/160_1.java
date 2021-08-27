//,temp,LlapTaskCommunicator.java,956,963,temp,LlapTaskUmbilicalExternalClient.java,515,524
//,3
public class xxx {
    @Override
    public void nodeHeartbeat(Text hostname, Text uniqueId, int port,
        TezAttemptArray aw, BooleanArray guaranteed) throws IOException {
      if (LOG.isDebugEnabled()) {
        LOG.debug("Received heartbeat from [" + hostname + ":" + port +" (" + uniqueId +")]");
      }
      nodePinged(hostname.toString(), uniqueId.toString(), port, aw, guaranteed);
    }

};