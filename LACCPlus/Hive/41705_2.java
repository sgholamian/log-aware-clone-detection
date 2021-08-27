//,temp,LlapTaskCommunicator.java,402,411,temp,LlapTaskSchedulerService.java,170,177
//,3
public class xxx {
    @Override
    public void setDone(Void v, QueryIdentifierProto result) {
      LOG.info("Dag with"
          + " appId=" + result.getApplicationIdString()
          + " dagId=" + result.getDagIndex()
          + " registered successfully for node " + nodeInfo.getHost());
      addNode(nodeInfo, llapServiceInstance);
    }

};