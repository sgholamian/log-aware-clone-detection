//,temp,SecurityUtil.java,118,130,temp,NMTokenSecretManagerInNM.java,249,254
//,3
public class xxx {
  public synchronized void setNodeId(NodeId nodeId) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("updating nodeId : " + nodeId);
    }
    this.nodeId = nodeId;
  }

};