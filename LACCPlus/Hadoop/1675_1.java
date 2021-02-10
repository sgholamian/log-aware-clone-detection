//,temp,DistCp.java,451,460,temp,DistCpSync.java,121,130
//,3
public class xxx {
  private synchronized void cleanup() {
    try {
      if (metaFolder == null) return;

      jobFS.delete(metaFolder, true);
      metaFolder = null;
    } catch (IOException e) {
      LOG.error("Unable to cleanup meta folder: " + metaFolder, e);
    }
  }

};