//,temp,TestDistCpUtils.java,1038,1048,temp,DistCp.java,451,460
//,3
public class xxx {
  public static void delete(FileSystem fs, String path) {
    try {
      if (fs != null) {
        if (path != null) {
          fs.delete(new Path(path), true);
        }
      }
    } catch (IOException e) {
      LOG.warn("Exception encountered ", e);
    }
  }

};