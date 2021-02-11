//,temp,ViewFsTestSetup.java,102,116,temp,ViewFileSystemTestSetup.java,113,127
//,2
public class xxx {
  static void setUpHomeDir(Configuration conf, FileSystem fsTarget) {
    String homeDir = fsTarget.getHomeDirectory().toUri().getPath();
    int indexOf2ndSlash = homeDir.indexOf('/', 1);
    if (indexOf2ndSlash >0) {
      linkUpFirstComponents(conf, homeDir, fsTarget, "home dir");
    } else { // home dir is at root. Just link the home dir itse
      URI linkTarget = fsTarget.makeQualified(new Path(homeDir)).toUri();
      ConfigUtil.addLink(conf, homeDir, linkTarget);
      Log.info("Added link for home dir " + homeDir + "->" + linkTarget);
    }
    // Now set the root of the home dir for viewfs
    String homeDirRoot = fsTarget.getHomeDirectory().getParent().toUri().getPath();
    ConfigUtil.setHomeDirConf(conf, homeDirRoot);
    Log.info("Home dir base for viewfs" + homeDirRoot);  
  }

};