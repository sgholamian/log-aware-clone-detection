//,temp,DropFunctionHandler.java,36,42,temp,AddDefaultConstraintHandler.java,35,45
//,3
public class xxx {
  @Override
  public void handle(Context withinContext) throws Exception {
    LOG.debug("Processing#{} ADD_DEFAULTCONSTRAINT_MESSAGE message : {}", fromEventId(),
      eventMessageAsJSON);

    if (shouldReplicate(withinContext)) {
      DumpMetaData dmd = withinContext.createDmd(this);
      dmd.setPayload(eventMessageAsJSON);
      dmd.write();
    }
  }

};