//,temp,AddNotNullConstraintHandler.java,35,45,temp,OpenTxnHandler.java,37,46
//,3
public class xxx {
  @Override
  public void handle(Context withinContext) throws Exception {
    if (!ReplUtils.includeAcidTableInDump(withinContext.hiveConf)) {
      return;
    }
    LOG.info("Processing#{} OPEN_TXN message : {}", fromEventId(), eventMessageAsJSON);
    DumpMetaData dmd = withinContext.createDmd(this);
    dmd.setPayload(eventMessageAsJSON);
    dmd.write();
  }

};