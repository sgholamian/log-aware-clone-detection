//,temp,BaseContainerTokenSecretManager.java,87,101,temp,BaseNMTokenSecretManager.java,72,87
//,3
public class xxx {
  @Override
  public byte[] createPassword(ContainerTokenIdentifier identifier) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("Creating password for " + identifier.getContainerID()
          + " for user " + identifier.getUser() + " to be run on NM "
          + identifier.getNmHostAddress());
    }
    this.readLock.lock();
    try {
      return createPassword(identifier.getBytes(),
        this.currentMasterKey.getSecretKey());
    } finally {
      this.readLock.unlock();
    }
  }

};