//,temp,ApplicationMaster.java,1211,1217,temp,HistoryFileManager.java,287,292
//,3
public class xxx {
    public void delete(HistoryFileInfo fileInfo) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("Removing from cache " + fileInfo);
      }
      cache.remove(fileInfo.getJobId());
    }

};