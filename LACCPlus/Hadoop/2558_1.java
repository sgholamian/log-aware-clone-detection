//,temp,LocalJavaKeyStoreProvider.java,66,73,temp,CGroupsResourceCalculator.java,146,152
//,3
public class xxx {
  @Override
  protected OutputStream getOutputStreamForKeystore() throws IOException {
    if (LOG.isDebugEnabled()) {
      LOG.debug("using '" + file + "' for output stream.");
    }
    FileOutputStream out = new FileOutputStream(file);
    return out;
  }

};