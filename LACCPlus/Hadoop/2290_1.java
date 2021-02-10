//,temp,ClusterNodeTracker.java,405,422,temp,ClusterNodeTracker.java,380,395
//,3
public class xxx {
  public List<NodeId> getNodeIdsByResourceName(final String resourceName) {
    Preconditions.checkArgument(
        resourceName != null && !resourceName.isEmpty());
    List<NodeId> retNodes = new ArrayList<>();
    if (ResourceRequest.ANY.equals(resourceName)) {
      retNodes.addAll(getAllNodeIds());
    } else if (nodeNameToNodeMap.containsKey(resourceName)) {
      retNodes.add(nodeNameToNodeMap.get(resourceName).getNodeID());
    } else if (nodesPerRack.containsKey(resourceName)) {
      for (N node : nodesPerRack.get(resourceName)) {
        retNodes.add(node.getNodeID());
      }
    } else {
      LOG.info(
          "Could not find a node matching given resourceName " + resourceName);
    }
    return retNodes;
  }

};