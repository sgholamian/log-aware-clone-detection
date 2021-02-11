//,temp,CompactTableAction.java,45,64,temp,FlushTableAction.java,41,60
//,3
public class xxx {
  @Override
  public void perform() throws Exception {
    HBaseTestingUtility util = context.getHBaseIntegrationTestingUtility();
    Admin admin = util.getAdmin();

    // Don't try the flush if we're stopping
    if (context.isStopping()) {
      return;
    }

    LOG.info("Performing action: Flush table " + tableName);
    try {
      admin.flush(tableName);
    } catch (Exception ex) {
      LOG.warn("Flush failed, might be caused by other chaos: " + ex.getMessage());
    }
    if (sleepTime > 0) {
      Thread.sleep(sleepTime);
    }
  }

};