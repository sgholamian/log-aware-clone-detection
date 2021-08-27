//,temp,DropConstraintHandler.java,35,42,temp,DropTableHandler.java,37,50
//,3
public class xxx {
  @Override
  public void handle(Context withinContext) throws Exception {
    LOG.info("Processing#{} DROP_CONSTRAINT_MESSAGE message : {}", fromEventId(),
        eventMessageAsJSON);
    DumpMetaData dmd = withinContext.createDmd(this);
    dmd.setPayload(eventMessageAsJSON);
    dmd.write();
  }

};