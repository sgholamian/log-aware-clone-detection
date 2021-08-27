//,temp,Hive.java,5541,5549,temp,Hive.java,4147,4154
//,3
public class xxx {
  public void cancelDelegationToken(String tokenStrForm)
    throws HiveException {
    try {
      getMSC().cancelDelegationToken(tokenStrForm);
    }  catch(Exception e) {
      LOG.error("Failed cancelDelegationToken", e);
      throw new HiveException(e);
    }
  }

};