//,temp,ViewFsTestSetup.java,122,133,temp,ViewFileSystemTestSetup.java,133,143
//,2
public class xxx {
  static void linkUpFirstComponents(Configuration conf, String path, FileSystem fsTarget, String info) {
    int indexOfEnd = path.indexOf('/', 1);
    if (Shell.WINDOWS) {
      indexOfEnd = path.indexOf('/', indexOfEnd + 1);
    }
    String firstComponent = path.substring(0, indexOfEnd);
    URI linkTarget = fsTarget.makeQualified(new Path(firstComponent)).toUri();
    ConfigUtil.addLink(conf, firstComponent, linkTarget);
    Log.info("Added link for " + info + " " 
        + firstComponent + "->" + linkTarget);    
  }

};