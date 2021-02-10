//,temp,ContainerExecutor.java,801,815,temp,JobHistoryEventHandler.java,588,599
//,3
public class xxx {
  public void closeWriter(JobId id) throws IOException {
    try {
      final MetaInfo mi = fileMap.get(id);
      if (mi != null) {
        mi.closeWriter();
      }
      
    } catch (IOException e) {
      LOG.error("Error closing writer for JobID: " + id);
      throw e;
    }
  }

};