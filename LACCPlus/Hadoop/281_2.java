//,temp,NetworkTopologyWithNodeGroup.java,223,249,temp,NetworkTopologyWithNodeGroup.java,171,217
//,3
public class xxx {
  @Override
  public void add(Node node) {
    if (node==null) return;
    if( node instanceof InnerNode ) {
      throw new IllegalArgumentException(
        "Not allow to add an inner node: "+NodeBase.getPath(node));
    }
    netlock.writeLock().lock();
    try {
      Node rack = null;

      // if node only with default rack info, here we need to add default 
      // nodegroup info
      if (NetworkTopology.DEFAULT_RACK.equals(node.getNetworkLocation())) {
        node.setNetworkLocation(node.getNetworkLocation() + 
            NetworkTopologyWithNodeGroup.DEFAULT_NODEGROUP);
      }
      Node nodeGroup = getNode(node.getNetworkLocation());
      if (nodeGroup == null) {
        nodeGroup = new InnerNodeWithNodeGroup(node.getNetworkLocation());
      }
      rack = getNode(nodeGroup.getNetworkLocation());

      // rack should be an innerNode and with parent.
      // note: rack's null parent case is: node's topology only has one layer, 
      //       so rack is recognized as "/" and no parent. 
      // This will be recognized as a node with fault topology.
      if (rack != null && 
          (!(rack instanceof InnerNode) || rack.getParent() == null)) {
        throw new IllegalArgumentException("Unexpected data node " 
            + node.toString() 
            + " at an illegal network location");
      }
      if (clusterMap.add(node)) {
        LOG.info("Adding a new node: " + NodeBase.getPath(node));
        if (rack == null) {
          // We only track rack number here
          numOfRacks++;
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