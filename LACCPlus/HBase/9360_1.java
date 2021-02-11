//,temp,FavoredNodeAssignmentHelper.java,146,167,temp,FavoredNodeAssignmentHelper.java,126,138
//,3
public class xxx {
  public static void updateMetaWithFavoredNodesInfo(
      Map<RegionInfo, List<ServerName>> regionToFavoredNodes,
      Configuration conf) throws IOException {
    List<Put> puts = new ArrayList<>();
    for (Map.Entry<RegionInfo, List<ServerName>> entry : regionToFavoredNodes.entrySet()) {
      Put put = makePutFromRegionInfo(entry.getKey(), entry.getValue());
      if (put != null) {
        puts.add(put);
      }
    }
    // Write the region assignments to the meta table.
    // TODO: See above overrides take a Connection rather than a Configuration only the
    // Connection is a short circuit connection. That is not going to good in all cases, when
    // master and meta are not colocated. Fix when this favored nodes feature is actually used
    // someday.
    try (Connection connection = ConnectionFactory.createConnection(conf)) {
      try (Table metaTable = connection.getTable(TableName.META_TABLE_NAME)) {
        metaTable.put(puts);
      }
    }
    LOG.info("Added " + puts.size() + " regions in META");
  }

};