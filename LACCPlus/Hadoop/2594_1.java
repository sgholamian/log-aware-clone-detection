//,temp,ApplicationMaster.java,1211,1217,temp,BackupImage.java,323,328
//,3
public class xxx {
    @Override
    public void onContainerStopped(ContainerId containerId) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("Succeeded to stop Container " + containerId);
      }
      containers.remove(containerId);
    }

};