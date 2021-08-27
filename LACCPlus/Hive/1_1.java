//,temp,DropPartitionHandler.java,39,53,temp,AllocWriteIdHandler.java,37,64
//,3
public class xxx {
  @Override
  public void handle(Context withinContext) throws Exception {
    LOG.info("Processing#{} DROP_PARTITION message : {}", fromEventId(), eventMessageAsJSON);

    // We do not dump partitions during metadata only bootstrap dump (See TableExport
    // .getPartitions(), for bootstrap dump we pass tableSpec with TABLE_ONLY set.). So don't
    // dump partition related events for metadata-only dump.
    if (withinContext.hiveConf.getBoolVar(HiveConf.ConfVars.REPL_DUMP_METADATA_ONLY)) {
      return;
    }

    DumpMetaData dmd = withinContext.createDmd(this);
    dmd.setPayload(eventMessageAsJSON);
    dmd.write();
  }

};