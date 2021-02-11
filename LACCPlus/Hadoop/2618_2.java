//,temp,RandomTextDataGenerator.java,105,112,temp,ApplicationMaster.java,1211,1217
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