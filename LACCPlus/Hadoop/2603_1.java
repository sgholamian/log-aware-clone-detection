//,temp,NMTokenSecretManagerInNM.java,249,254,temp,PBImageTextWriter.java,638,644
//,3
public class xxx {
  public synchronized void setNodeId(NodeId nodeId) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("updating nodeId : " + nodeId);
    }
    this.nodeId = nodeId;
  }

};