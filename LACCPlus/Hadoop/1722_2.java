//,temp,BlockPlacementPolicyDefault.java,583,607,temp,BlockPlacementPolicyDefault.java,554,574
//,3
public class xxx {
  private DatanodeStorageInfo chooseFromNextRack(Node next,
      Set<Node> excludedNodes,
      long blocksize,
      int maxNodesPerRack,
      List<DatanodeStorageInfo> results,
      boolean avoidStaleNodes,
      EnumMap<StorageType, Integer> storageTypes) throws NotEnoughReplicasException {
    final String nextRack = next.getNetworkLocation();
    try {
      return chooseRandom(nextRack, excludedNodes, blocksize, maxNodesPerRack,
          results, avoidStaleNodes, storageTypes);
    } catch(NotEnoughReplicasException e) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("Failed to choose from the next rack (location = " + nextRack
            + "), retry choosing ramdomly", e);
      }
      //otherwise randomly choose one from the network
      return chooseRandom(NodeBase.ROOT, excludedNodes, blocksize,
          maxNodesPerRack, results, avoidStaleNodes, storageTypes);
    }
  }

};