//,temp,MergeRandomAdjacentRegionsOfTableAction.java,45,75,temp,CompactRandomRegionOfTableAction.java,50,81
//,3
public class xxx {
  @Override
  public void perform() throws Exception {
    HBaseTestingUtility util = context.getHBaseIntegrationTestingUtility();
    Admin admin = util.getAdmin();

    LOG.info("Performing action: Merge random adjacent regions of table " + tableName);
    List<HRegionInfo> regions = admin.getTableRegions(tableName);
    if (regions == null || regions.size() < 2) {
      LOG.info("Table " + tableName + " doesn't have enough regions to merge");
      return;
    }

    int i = RandomUtils.nextInt(0, regions.size() - 1);
    HRegionInfo a = regions.get(i++);
    HRegionInfo b = regions.get(i);
    LOG.debug("Merging " + a.getRegionNameAsString() + " and " + b.getRegionNameAsString());

    // Don't try the merge if we're stopping
    if (context.isStopping()) {
      return;
    }

    try {
      admin.mergeRegionsAsync(a.getEncodedNameAsBytes(), b.getEncodedNameAsBytes(), false);
    } catch (Exception ex) {
      LOG.warn("Merge failed, might be caused by other chaos: " + ex.getMessage());
    }
    if (sleepTime > 0) {
      Thread.sleep(sleepTime);
    }
  }

};