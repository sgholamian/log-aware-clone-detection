//,temp,NativeS3FileSystem.java,273,293,temp,S3InputStream.java,178,195
//,3
public class xxx {
  @Override
  public void close() throws IOException {
    if (closed) {
      return;
    }
    if (blockStream != null) {
      blockStream.close();
      blockStream = null;
    }
    if (blockFile != null) {
      boolean b = blockFile.delete();
      if (!b) {
        LOG.warn("Ignoring failed delete");
      }
    }
    super.close();
    closed = true;
  }

};