//,temp,SwiftNativeFileSystem.java,426,432,temp,LocalJavaKeyStoreProvider.java,66,73
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