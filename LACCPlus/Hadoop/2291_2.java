//,temp,NetworkTopology.java,213,232,temp,NetworkTopologyWithNodeGroup.java,228,254
//,3
public class xxx {
  @Override
  public void remove(Node node) {
    if (node==null) return;
    if( node instanceof InnerNode ) {
      throw new IllegalArgumentException(
          "Not allow to remove an inner node: "+NodeBase.getPath(node));
    }
    LOG.info("Removing a node: "+NodeBase.getPath(node));
    netlock.writeLock().lock();
    try {
      if (clusterMap.remove(node)) {
        Node nodeGroup = getNode(node.getNetworkLocation());
        if (nodeGroup == null) {
          nodeGroup = factory.newInnerNode(node.getNetworkLocation());
        }
        InnerNode rack = (InnerNode)getNode(nodeGroup.getNetworkLocation());
        if (rack == null) {
          numOfRacks--;
        }
      }
      if(LOG.isDebugEnabled()) {
        LOG.debug("NetworkTopology became:\n" + this.toString());
      }
    } finally {
      netlock.writeLock().unlock();
    }
  }

};