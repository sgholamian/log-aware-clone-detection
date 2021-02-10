//,temp,AMRMProxyTokenSecretManager.java,148,168,temp,AMRMTokenSecretManager.java,138,154
//,3
public class xxx {
  @Private
  @VisibleForTesting
  public void rollMasterKey() {
    this.writeLock.lock();
    try {
      LOG.info("Rolling master-key for amrm-tokens");
      this.nextMasterKey = createNewMasterKey();
      if (this.nmStateStore != null) {
        try {
          this.nmStateStore
              .storeAMRMProxyNextMasterKey(this.nextMasterKey.getMasterKey());
        } catch (IOException e) {
          LOG.error("Unable to update next master key in state store", e);
        }
      }

      this.timer.schedule(new NextKeyActivator(), this.activationDelay);
    } finally {
      this.writeLock.unlock();
    }
  }

};