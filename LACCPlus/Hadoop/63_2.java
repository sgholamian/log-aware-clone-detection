//,temp,ZKDelegationTokenSecretManager.java,612,629,temp,ZKDelegationTokenSecretManager.java,573,589
//,3
public class xxx {
  @Override
  protected DelegationKey getDelegationKey(int keyId) {
    // First check if its I already have this key
    DelegationKey key = allKeys.get(keyId);
    // Then query ZK
    if (key == null) {
      try {
        key = getKeyFromZK(keyId);
        if (key != null) {
          allKeys.put(keyId, key);
        }
      } catch (IOException e) {
        LOG.error("Error retrieving key [" + keyId + "] from ZK", e);
      }
    }
    return key;
  }

};