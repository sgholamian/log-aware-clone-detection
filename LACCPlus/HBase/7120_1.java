//,temp,TestActiveMasterManager.java,245,251,temp,TestZKNodeTracker.java,283,289
//,2
public class xxx {
    @Override
    public void nodeDeleted(String path) {
      if(path.equals(node)) {
        LOG.debug("nodeDeleted(" + path + ")");
        lock.release();
      }
    }

};