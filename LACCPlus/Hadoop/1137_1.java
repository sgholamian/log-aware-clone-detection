//,temp,ZKDelegationTokenSecretManager.java,559,571,temp,ZKDelegationTokenSecretManager.java,531,543
//,2
public class xxx {
  @Override
  protected int incrementCurrentKeyId() {
    try {
      incrSharedCount(keyIdSeqCounter);
    } catch (InterruptedException e) {
      // The ExpirationThread is just finishing.. so dont do anything..
      LOG.debug("Thread interrupted while performing keyId increment", e);
      Thread.currentThread().interrupt();
    } catch (Exception e) {
      throw new RuntimeException("Could not increment shared keyId counter !!", e);
    }
    return keyIdSeqCounter.getCount();
  }

};