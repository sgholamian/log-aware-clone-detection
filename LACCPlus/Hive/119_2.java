//,temp,ObjectStore.java,10272,10292,temp,ObjectStore.java,10204,10224
//,3
public class xxx {
  @Override
  public boolean removeToken(String tokenId) {

    LOG.debug("Begin executing removeToken");
    boolean committed = false;
    MDelegationToken token;
    try{
      openTransaction();
      token = getTokenFrom(tokenId);
      if (null != token) {
        pm.deletePersistent(token);
      }
      committed = commitTransaction();
    } finally {
      if(!committed) {
        rollbackTransaction();
      }
    }
    LOG.debug("Done executing removeToken with status : {}", committed);
    return committed && (token != null);
  }

};