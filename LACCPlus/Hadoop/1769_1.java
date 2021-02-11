//,temp,NativeS3FileSystem.java,273,293,temp,S3InputStream.java,178,195
//,3
public class xxx {
    @Override
    public synchronized void close() throws IOException {
      if (closed) {
        return;
      }

      backupStream.close();
      LOG.info("OutputStream for key '{}' closed. Now beginning upload", key);
      
      try {
        byte[] md5Hash = digest == null ? null : digest.digest();
        store.storeFile(key, backupFile, md5Hash);
      } finally {
        if (!backupFile.delete()) {
          LOG.warn("Could not delete temporary s3n file: " + backupFile);
        }
        super.close();
        closed = true;
      } 
      LOG.info("OutputStream for key '{}' upload complete", key);
    }

};