//,temp,DeletePartColStatHandler.java,36,42,temp,AddPrimaryKeyHandler.java,35,45
//,3
public class xxx {
  @Override
  public void handle(Context withinContext) throws Exception {
    LOG.debug("Processing#{} ADD_PRIMARYKEY_MESSAGE message : {}", fromEventId(),
        eventMessageAsJSON);

    if (shouldReplicate(withinContext)) {
      DumpMetaData dmd = withinContext.createDmd(this);
      dmd.setPayload(eventMessageAsJSON);
      dmd.write();
    }
  }

};