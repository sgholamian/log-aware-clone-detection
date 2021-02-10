//,temp,RMContainerTokenSecretManager.java,138,149,temp,NMTokenSecretManagerInRM.java,141,152
//,3
public class xxx {
  @Private
  public void activateNextMasterKey() {
    super.writeLock.lock();
    try {
      LOG.info("Activating next master key with id: "
          + this.nextMasterKey.getMasterKey().getKeyId());
      this.currentMasterKey = this.nextMasterKey;
      this.nextMasterKey = null;
    } finally {
      super.writeLock.unlock();
    }
  }

};