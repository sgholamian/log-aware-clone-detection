//,temp,AddUniqueConstraintHandler.java,36,46,temp,AbortTxnHandler.java,37,46
//,3
public class xxx {
  @Override
  public void handle(Context withinContext) throws Exception {
    LOG.debug("Processing#{} ADD_UNIQUECONSTRAINT_MESSAGE message : {}", fromEventId(),
        eventMessageAsJSON);

    if (shouldReplicate(withinContext)) {
      DumpMetaData dmd = withinContext.createDmd(this);
      dmd.setPayload(eventMessageAsJSON);
      dmd.write();
    }
  }

};