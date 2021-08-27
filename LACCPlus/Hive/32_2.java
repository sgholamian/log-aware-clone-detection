//,temp,Utilities.java,4279,4285,temp,SkewJoinHandler.java,280,286
//,3
public class xxx {
  private void delete(Path operatorOutputPath, FileSystem fs) {
    try {
      fs.delete(operatorOutputPath, true);
    } catch (IOException e) {
      LOG.error("Failed to delete path ", e);
    }
  }

};