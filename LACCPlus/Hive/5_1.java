//,temp,DeletePartColStatHandler.java,36,42,temp,AddPrimaryKeyHandler.java,35,45
//,3
public class xxx {
  @Override
  public void handle(Context withinContext) throws Exception {
    LOG.info("Processing#{} DeletePartitionColumnStatMessage message : {}", fromEventId(), eventMessageAsJSON);
    DumpMetaData dmd = withinContext.createDmd(this);
    dmd.setPayload(eventMessageAsJSON);
    dmd.write();
  }

};