//,temp,sample_3144.java,2,15,temp,sample_424.java,2,14
//,3
public class xxx {
public Set<RegionInfo> getMisplacedRegions( Map<RegionInfo, ServerName> regions) throws IOException {
Set<RegionInfo> misplacedRegions = new HashSet<>();
for(Map.Entry<RegionInfo, ServerName> region : regions.entrySet()) {
RegionInfo regionInfo = region.getKey();
ServerName assignedServer = region.getValue();
RSGroupInfo info = rsGroupInfoManager.getRSGroup(rsGroupInfoManager. getRSGroupOfTable(regionInfo.getTable()));
if (assignedServer == null) {


log.info("there is no assigned server for");
}
}
}

};