//,temp,MergeRandomAdjacentRegionsOfTableAction.java,45,75,temp,CompactRandomRegionOfTableAction.java,50,81
//,3
public class xxx {
  @Override
  public void perform() throws Exception {
    HBaseTestingUtility util = context.getHBaseIntegrationTestingUtility();
    Admin admin = util.getAdmin();
    boolean major = RandomUtils.nextInt(0, 100) < majorRatio;

    LOG.info("Performing action: Compact random region of table "
      + tableName + ", major=" + major);
    List<HRegionInfo> regions = admin.getTableRegions(tableName);
    if (regions == null || regions.isEmpty()) {
      LOG.info("Table " + tableName + " doesn't have regions to compact");
      return;
    }

    HRegionInfo region = PolicyBasedChaosMonkey.selectRandomItem(
      regions.toArray(new HRegionInfo[regions.size()]));

    try {
      if (major) {
        LOG.debug("Major compacting region " + region.getRegionNameAsString());
        admin.majorCompactRegion(region.getRegionName());
      } else {
        LOG.debug("Compacting region " + region.getRegionNameAsString());
        admin.compactRegion(region.getRegionName());
      }
    } catch (Exception ex) {
      LOG.warn("Compaction failed, might be caused by other chaos: " + ex.getMessage());
    }
    if (sleepTime > 0) {
      Thread.sleep(sleepTime);
    }
  }

};