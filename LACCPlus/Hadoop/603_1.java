//,temp,NameNodeConnector.java,262,275,temp,S3InputStream.java,178,195
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