//,temp,DropConstraintHandler.java,35,42,temp,DropTableHandler.java,37,50
//,3
public class xxx {
  @Override
  public void handle(Context withinContext) throws Exception {
    LOG.info("Processing#{} DROP_TABLE message : {}", fromEventId(), eventMessageAsJSON);

    // If table is present in the list of tables to be bootstrapped, then remove it. Drop event can be ignored as
    // table will not be present at target. Anyways all the events related to this table is ignored.
    if (withinContext.removeFromListOfTablesForBootstrap(event.getTableName())) {
      LOG.info("Table " + event.getTableName() + " is removed from list of tables to be bootstrapped.");
      return;
    }
    DumpMetaData dmd = withinContext.createDmd(this);
    dmd.setPayload(eventMessageAsJSON);
    dmd.write();
  }

};