//,temp,AbstractJavaKeyStoreProvider.java,161,167,temp,ApplicationMaster.java,1211,1217
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