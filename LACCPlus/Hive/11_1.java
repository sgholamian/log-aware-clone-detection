//,temp,AddNotNullConstraintHandler.java,35,45,temp,OpenTxnHandler.java,37,46
//,3
public class xxx {
  @Override
  public void handle(Context withinContext) throws Exception {
    LOG.debug("Processing#{} ADD_NOTNULLCONSTRAINT_MESSAGE message : {}", fromEventId(),
        eventMessageAsJSON);

    if (shouldReplicate(withinContext)) {
      DumpMetaData dmd = withinContext.createDmd(this);
      dmd.setPayload(eventMessageAsJSON);
      dmd.write();
    }
  }

};