//,temp,TestActiveMasterManager.java,245,251,temp,TestZKNodeTracker.java,283,289
//,2
public class xxx {
    @Override
    public void nodeDataChanged(String path) {
      if(path.equals(node)) {
        LOG.debug("nodeDataChanged(" + path + ")");
        changedLock.release();
      }
    }

};