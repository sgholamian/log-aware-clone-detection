//,temp,AlterDatabaseHandler.java,36,42,temp,DeleteTableColStatHandler.java,36,42
//,2
public class xxx {
  @Override
  public void handle(Context withinContext) throws Exception {
    LOG.info("Processing#{} DeleteTableColumnStat message : {}", fromEventId(), eventMessageAsJSON);
    DumpMetaData dmd = withinContext.createDmd(this);
    dmd.setPayload(eventMessageAsJSON);
    dmd.write();
  }

};