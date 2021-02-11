//,temp,MetaTableAccessor.java,1844,1855,temp,MetaTableAccessor.java,1822,1828
//,3
public class xxx {
  public static void deleteRegion(Connection connection, RegionInfo regionInfo) throws IOException {
    long time = EnvironmentEdgeManager.currentTime();
    Delete delete = new Delete(regionInfo.getRegionName());
    delete.addFamily(getCatalogFamily(), time);
    deleteFromMetaTable(connection, delete);
    LOG.info("Deleted " + regionInfo.getRegionNameAsString());
  }

};