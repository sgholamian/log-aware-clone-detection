//,temp,RMContainerTokenSecretManager.java,102,119,temp,AMRMTokenSecretManager.java,138,154
//,3
public class xxx {
  @Private
  void rollMasterKey() {
    this.writeLock.lock();
    try {
      LOG.info("Rolling master-key for amrm-tokens");
      this.nextMasterKey = createNewMasterKey();
      AMRMTokenSecretManagerState state =
          AMRMTokenSecretManagerState.newInstance(
            this.currentMasterKey.getMasterKey(),
            this.nextMasterKey.getMasterKey());
      rmContext.getStateStore()
          .storeOrUpdateAMRMTokenSecretManager(state, true);
      this.timer.schedule(new NextKeyActivator(), this.activationDelay);
    } finally {
      this.writeLock.unlock();
    }
  }

};