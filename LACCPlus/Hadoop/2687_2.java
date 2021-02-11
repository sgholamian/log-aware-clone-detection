//,temp,TestSwiftFileSystemBasicOps.java,119,127,temp,AzureTestUtils.java,419,429
//,3
public class xxx {
  public static void deleteQuietly(FileSystem fs,
      Path path,
      boolean recursive) throws IOException {
    if (fs != null && path != null) {
      try {
        fs.delete(path, recursive);
      } catch (IOException e) {
        LOG.warn("When deleting {}", path, e);
      }
    }
  }

};