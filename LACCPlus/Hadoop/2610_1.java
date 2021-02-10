//,temp,FileSystemContractBaseTest.java,89,96,temp,YarnRegistryViewForProviders.java,220,227
//,3
public class xxx {
  private void cleanupDir(Path p) {
    try {
      LOG.info("Deleting " + p);
      fs.delete(p, true);
    } catch (IOException e) {
      LOG.error("Error deleting test dir: " + p, e);
    }
  }

};