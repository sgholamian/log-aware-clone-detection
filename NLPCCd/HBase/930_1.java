//,temp,sample_5948.java,2,17,temp,sample_5949.java,2,17
//,2
public class xxx {
public void dummy_method(){
RegionLocations url = ((ClusterConnection) HTU.getConnection()) .locateRegion(hdt.getTableName(), row, false, false);
if (!url.getDefaultRegionLocation().getServerName().equals( mrl.getDefaultRegionLocation().getServerName())) {
HTU.moveRegionAndWait(url.getDefaultRegionLocation().getRegionInfo(), mrl.getDefaultRegionLocation().getServerName());
}
if (url.getRegionLocation(1).getServerName().equals(mrl.getDefaultRegionLocation() .getServerName())) {
HTU.moveRegionAndWait(url.getRegionLocation(1).getRegionInfo(), url.getDefaultRegionLocation().getServerName());
}
while (true) {
mrl = ((ClusterConnection) HTU.getConnection()) .locateRegion(TableName.META_TABLE_NAME, HConstants.EMPTY_START_ROW, false, false);
url = ((ClusterConnection) HTU.getConnection()) .locateRegion(hdt.getTableName(), row, false, true);


log.info("meta locations");
}
}

};