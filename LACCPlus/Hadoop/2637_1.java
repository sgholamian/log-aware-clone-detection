//,temp,DistCp.java,454,465,temp,FederationRegistryClient.java,266,277
//,3
public class xxx {
  private synchronized void cleanup() {
    try {
      if (metaFolder != null) {
        if (jobFS != null) {
          jobFS.delete(metaFolder, true);
        }
        metaFolder = null;
      }
    } catch (IOException e) {
      LOG.error("Unable to cleanup meta folder: " + metaFolder, e);
    }
  }

};