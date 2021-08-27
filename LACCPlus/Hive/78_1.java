//,temp,AddCheckConstraintHandler.java,35,45,temp,DropDatabaseHandler.java,35,41
//,3
public class xxx {
  @Override
  public void handle(Context withinContext) throws Exception {
    LOG.debug("Processing#{} ADD_CHECKCONSTRAINT_MESSAGE message : {}", fromEventId(),
      eventMessageAsJSON);

    if (shouldReplicate(withinContext)) {
      DumpMetaData dmd = withinContext.createDmd(this);
      dmd.setPayload(eventMessageAsJSON);
      dmd.write();
    }
  }

};