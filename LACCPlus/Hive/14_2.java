//,temp,AddUniqueConstraintHandler.java,36,46,temp,AbortTxnHandler.java,37,46
//,3
public class xxx {
  @Override
  public void handle(Context withinContext) throws Exception {
    if (!ReplUtils.includeAcidTableInDump(withinContext.hiveConf)) {
      return;
    }
    LOG.info("Processing#{} ABORT_TXN message : {}", fromEventId(), eventMessageAsJSON);
    DumpMetaData dmd = withinContext.createDmd(this);
    dmd.setPayload(eventMessageAsJSON);
    dmd.write();
  }

};