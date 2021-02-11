//,temp,sample_3144.java,2,15,temp,sample_4707.java,2,14
//,3
public class xxx {
public static void updateMetaWithFavoredNodesInfo( Map<RegionInfo, List<ServerName>> regionToFavoredNodes, Connection connection) throws IOException {
List<Put> puts = new ArrayList<>();
for (Map.Entry<RegionInfo, List<ServerName>> entry : regionToFavoredNodes.entrySet()) {
Put put = makePutFromRegionInfo(entry.getKey(), entry.getValue());
if (put != null) {
puts.add(put);
}
}
MetaTableAccessor.putsToMetaTable(connection, puts);


log.info("added regions in meta");
}

};