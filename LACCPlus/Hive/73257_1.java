//,temp,UpdatePartColStatHandler.java,38,63,temp,UpdateTableColStatHandler.java,36,54
//,3
public class xxx {
  @Override
  public void handle(Context withinContext) throws Exception {
    LOG.info("Processing#{} UpdatePartitionTableColumnStat message : {}", fromEventId(),
            eventMessageAsJSON);

    org.apache.hadoop.hive.metastore.api.Table tableObj = eventMessage.getTableObject();
    if (tableObj == null) {
      LOG.debug("Event#{} was an event of type {} with no table listed", fromEventId(),
              event.getEventType());
      return;
    }
    // Statistics without any data does not make sense.
    if (withinContext.replicationSpec.isMetadataOnly()
            || Utils.shouldDumpMetaDataOnlyForExternalTables(new Table(tableObj), withinContext.hiveConf)) {
      return;
    }

    if (!Utils.shouldReplicate(withinContext.replicationSpec, new Table(tableObj), true,
            withinContext.getTablesForBootstrap(), withinContext.oldReplScope, withinContext.hiveConf)) {
      return;
    }

    DumpMetaData dmd = withinContext.createDmd(this);
    dmd.setPayload(eventMessageAsJSON);
    dmd.write();
  }

};