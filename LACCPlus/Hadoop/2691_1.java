//,temp,AtomicFileOutputStream.java,107,116,temp,NameNodeConnector.java,272,285
//,3
public class xxx {
  public void abort() {
    try {
      super.close();
    } catch (IOException ioe) {
      LOG.warn("Unable to abort file " + tmpFile, ioe);
    }
    if (!tmpFile.delete()) {
      LOG.warn("Unable to delete tmp file during abort " + tmpFile);
    }
  }

};