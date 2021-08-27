//,temp,DropPartitionHandler.java,39,53,temp,AllocWriteIdHandler.java,37,64
//,3
public class xxx {
  @Override
  public void handle(Context withinContext) throws Exception {
    LOG.info("Processing#{} ALLOC_WRITE_ID message : {}", fromEventId(), eventMessageAsJSON);

    // If we are bootstrapping ACID table during an incremental dump, the events corresponding to
    // these ACID tables are not dumped. Hence we do not need to allocate any writeId on the
    // target and hence we do not need to dump these events.
    if (withinContext.hiveConf.getBoolVar(HiveConf.ConfVars.REPL_BOOTSTRAP_ACID_TABLES)) {
      return;
    }

    if (!ReplUtils.includeAcidTableInDump(withinContext.hiveConf)) {
      return;
    }

    // If replication policy is replaced with new included/excluded tables list, then events
    // corresponding to tables which are not included in old policy but included in new policy
    // should be skipped. Those tables would be bootstrapped along with the current incremental
    // replication dump.
    // Note: If any event dump reaches here, it means, it is included in new replication policy.
    if (!ReplUtils.tableIncludedInReplScope(withinContext.oldReplScope, eventMessage.getTableName())) {
      return;
    }

    DumpMetaData dmd = withinContext.createDmd(this);
    dmd.setPayload(eventMessageAsJSON);
    dmd.write();
  }

};