//,temp,sample_2302.java,2,18,temp,sample_2301.java,2,18
//,3
public class xxx {
public void dummy_method(){
HTableDescriptor htd = tbl.getTableDescriptor();
dumpMeta(htd);
List<HRegionLocation> regions;
try(RegionLocator rl = connection.getRegionLocator(tbl.getName())) {
regions = rl.getAllRegionLocations();
}
for (HRegionLocation e : regions) {
RegionInfo hri = e.getRegionInfo();
ServerName hsa = e.getServerName();
if (Bytes.compareTo(hri.getStartKey(), startKey) == 0 && Bytes.compareTo(hri.getEndKey(), endKey) == 0) {


log.info("regionname");
}
}
}

};