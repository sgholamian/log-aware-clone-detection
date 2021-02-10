//,temp,RMContainerTokenSecretManager.java,102,119,temp,AMRMTokenSecretManager.java,138,154
//,3
public class xxx {
  @Private
  public void rollMasterKey() {
    super.writeLock.lock();
    try {
      LOG.info("Rolling master-key for container-tokens");
      if (this.currentMasterKey == null) { // Setting up for the first time.
        this.currentMasterKey = createNewMasterKey();
      } else {
        this.nextMasterKey = createNewMasterKey();
        LOG.info("Going to activate master-key with key-id "
            + this.nextMasterKey.getMasterKey().getKeyId() + " in "
            + this.activationDelay + "ms");
        this.timer.schedule(new NextKeyActivator(), this.activationDelay);
      }
    } finally {
      super.writeLock.unlock();
    }
  }

};