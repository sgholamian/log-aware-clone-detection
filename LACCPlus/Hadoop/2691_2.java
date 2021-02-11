//,temp,AtomicFileOutputStream.java,107,116,temp,NameNodeConnector.java,272,285
//,3
public class xxx {
  @Override
  public void close() {
    keyManager.close();

    // close the output file
    IOUtils.closeStream(out); 
    if (fs != null) {
      try {
        fs.delete(idPath, true);
      } catch(IOException ioe) {
        LOG.warn("Failed to delete " + idPath, ioe);
      }
    }
  }

};