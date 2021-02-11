//,temp,CompactTableAction.java,45,64,temp,FlushTableAction.java,41,60
//,3
public class xxx {
  @Override
  public void perform() throws Exception {
    HBaseTestingUtility util = context.getHBaseIntegrationTestingUtility();
    Admin admin = util.getAdmin();
    boolean major = RandomUtils.nextInt(0, 100) < majorRatio;

    LOG.info("Performing action: Compact table " + tableName + ", major=" + major);
    try {
      if (major) {
        admin.majorCompact(tableName);
      } else {
        admin.compact(tableName);
      }
    } catch (Exception ex) {
      LOG.warn("Compaction failed, might be caused by other chaos: " + ex.getMessage());
    }
    if (sleepTime > 0) {
      Thread.sleep(sleepTime);
    }
  }

};