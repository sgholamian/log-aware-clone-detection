//,temp,Hive.java,5607,5614,temp,Hive.java,5531,5539
//,3
public class xxx {
  public String getDelegationToken(String owner, String renewer)
    throws HiveException{
    try {
      return getMSC().getDelegationToken(owner, renewer);
    } catch(Exception e) {
      LOG.error("Failed getDelegationToken", e);
      throw new HiveException(e);
    }
  }

};