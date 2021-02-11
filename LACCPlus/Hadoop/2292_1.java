//,temp,NativeAzureFileSystem.java,1116,1129,temp,NativeAzureFileSystem.java,1063,1076
//,3
public class xxx {
    @Override
    public void write(byte[] b, int off, int len) throws IOException {
      try {
        out.write(b, off, len);
      } catch(IOException e) {
        if (e.getCause() instanceof StorageException) {
          StorageException storageExcp  = (StorageException) e.getCause();
          LOG.error("Encountered Storage Exception for write on Blob : {}"
              + " Exception details: {} Error Code : {}",
              key, e.getMessage(), storageExcp.getErrorCode());
        }
        throw e;
      }
    }

};