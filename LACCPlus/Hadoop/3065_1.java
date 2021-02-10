//,temp,AMRMProxyTokenSecretManager.java,177,198,temp,RMContainerTokenSecretManager.java,105,122
//,3
public class xxx {
  @Private
  @VisibleForTesting
  public void activateNextMasterKey() {
    this.writeLock.lock();
    try {
      LOG.info("Activating next master key with id: "
          + this.nextMasterKey.getMasterKey().getKeyId());
      this.currentMasterKey = this.nextMasterKey;
      this.nextMasterKey = null;
      if (this.nmStateStore != null) {
        try {
          this.nmStateStore.storeAMRMProxyCurrentMasterKey(
              this.currentMasterKey.getMasterKey());
          this.nmStateStore.storeAMRMProxyNextMasterKey(null);
        } catch (IOException e) {
          LOG.error("Unable to update current master key in state store", e);
        }
      }
    } finally {
      this.writeLock.unlock();
    }
  }

};