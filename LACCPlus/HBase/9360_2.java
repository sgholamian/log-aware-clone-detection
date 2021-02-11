//,temp,FavoredNodeAssignmentHelper.java,146,167,temp,FavoredNodeAssignmentHelper.java,126,138
//,3
public class xxx {
  public static void updateMetaWithFavoredNodesInfo(
      Map<RegionInfo, List<ServerName>> regionToFavoredNodes,
      Connection connection) throws IOException {
    List<Put> puts = new ArrayList<>();
    for (Map.Entry<RegionInfo, List<ServerName>> entry : regionToFavoredNodes.entrySet()) {
      Put put = makePutFromRegionInfo(entry.getKey(), entry.getValue());
      if (put != null) {
        puts.add(put);
      }
    }
    MetaTableAccessor.putsToMetaTable(connection, puts);
    LOG.info("Added " + puts.size() + " regions in META");
  }

};