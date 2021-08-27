//,temp,UpdatePartColStatHandler.java,38,63,temp,UpdateTableColStatHandler.java,36,54
//,3
public class xxx {
  @Override
  public void handle(Context withinContext) throws Exception {
    LOG.info("Processing#{} UpdateTableColumnStat message : {}", fromEventId(), eventMessageAsJSON);
    Table qlMdTable = new Table(eventMessage.getTableObject());
    if (!Utils.shouldReplicate(withinContext.replicationSpec, qlMdTable, true,
            withinContext.getTablesForBootstrap(), withinContext.oldReplScope, withinContext.hiveConf)) {
      return;
    }

    // Statistics without data doesn't make sense.
    if (withinContext.replicationSpec.isMetadataOnly()
            || Utils.shouldDumpMetaDataOnlyForExternalTables(qlMdTable, withinContext.hiveConf)) {
      return;
    }

    DumpMetaData dmd = withinContext.createDmd(this);
    dmd.setPayload(eventMessageAsJSON);
    dmd.write();
  }

};