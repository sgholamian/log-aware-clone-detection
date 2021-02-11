//,temp,RMContainerTokenSecretManager.java,105,122,temp,AMRMTokenSecretManager.java,163,178
//,3
public class xxx {
  public void activateNextMasterKey() {
    this.writeLock.lock();
    try {
      LOG.info("Activating next master key with id: "
          + this.nextMasterKey.getMasterKey().getKeyId());
      this.currentMasterKey = this.nextMasterKey;
      this.nextMasterKey = null;
      AMRMTokenSecretManagerState state =
          AMRMTokenSecretManagerState.newInstance(
            this.currentMasterKey.getMasterKey(), null);
      rmContext.getStateStore()
          .storeOrUpdateAMRMTokenSecretManager(state, true);
    } finally {
      this.writeLock.unlock();
    }
  }

};