//,temp,Utilities.java,4279,4285,temp,SkewJoinHandler.java,280,286
//,3
public class xxx {
  private static void tryDelete(FileSystem fs, Path path) {
    try {
      fs.delete(path, true);
    } catch (IOException ex) {
      LOG.error("Failed to delete {}", path, ex);
    }
  }

};