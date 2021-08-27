//,temp,AddCheckConstraintHandler.java,35,45,temp,DropDatabaseHandler.java,35,41
//,3
public class xxx {
  @Override
  public void handle(Context withinContext) throws Exception {
    LOG.info("Processing#{} DROP_DATABASE message : {}", fromEventId(), eventMessageAsJSON);
    DumpMetaData dmd = withinContext.createDmd(this);
    dmd.setPayload(eventMessageAsJSON);
    dmd.write();
  }

};