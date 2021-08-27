//,temp,HMSHandler.java,10331,10349,temp,HMSHandler.java,10132,10151
//,3
public class xxx {
  @Override
  public SerDeInfo get_serde(GetSerdeRequest rqst) throws TException {
    startFunction("get_serde", ": " + rqst);
    Exception ex = null;
    SerDeInfo serde = null;
    try {
      serde = getMS().getSerDeInfo(rqst.getSerdeName());
      if (serde == null) {
        throw new NoSuchObjectException("No serde named " + rqst.getSerdeName() + " exists");
      }
      return serde;
    } catch (MetaException e) {
      LOG.error("Caught exception getting serde", e);
      ex = e;
      throw e;
    } finally {
      endFunction("get_serde", serde != null, ex);
    }
  }

};