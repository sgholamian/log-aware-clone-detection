//,temp,MetaTableAccessor.java,1844,1855,temp,MetaTableAccessor.java,1822,1828
//,3
public class xxx {
  public static void deleteRegions(Connection connection, List<RegionInfo> regionsInfo, long ts)
      throws IOException {
    List<Delete> deletes = new ArrayList<>(regionsInfo.size());
    for (RegionInfo hri : regionsInfo) {
      Delete e = new Delete(hri.getRegionName());
      e.addFamily(getCatalogFamily(), ts);
      deletes.add(e);
    }
    deleteFromMetaTable(connection, deletes);
    LOG.info("Deleted {} regions from META", regionsInfo.size());
    LOG.debug("Deleted regions: {}", regionsInfo);
  }

};