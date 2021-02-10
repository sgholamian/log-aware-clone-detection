//,temp,ViewFsTestSetup.java,57,89,temp,ViewFileSystemTestSetup.java,59,89
//,3
public class xxx {
  static public FileContext setupForViewFsLocalFs(FileContextTestHelper helper) throws Exception {
    /**
     * create the test root on local_fs - the  mount table will point here
     */
    FileContext fsTarget = FileContext.getLocalFSFileContext();
    Path targetOfTests = helper.getTestRootPath(fsTarget);
    // In case previous test was killed before cleanup
    fsTarget.delete(targetOfTests, true);
    
    fsTarget.mkdir(targetOfTests, FileContext.DEFAULT_PERM, true);
    Configuration conf = new Configuration();
    
    // Set up viewfs link for test dir as described above
    String testDir = helper.getTestRootPath(fsTarget).toUri()
        .getPath();
    linkUpFirstComponents(conf, testDir, fsTarget, "test dir");
    
    
    // Set up viewfs link for home dir as described above
    setUpHomeDir(conf, fsTarget);
      
    // the test path may be relative to working dir - we need to make that work:
    // Set up viewfs link for wd as described above
    String wdDir = fsTarget.getWorkingDirectory().toUri().getPath();
    linkUpFirstComponents(conf, wdDir, fsTarget, "working dir");
    
    FileContext fc = FileContext.getFileContext(FsConstants.VIEWFS_URI, conf);
    fc.setWorkingDirectory(new Path(wdDir)); // in case testdir relative to wd.
    Log.info("Working dir is: " + fc.getWorkingDirectory());
    //System.out.println("SRCOfTests = "+ getTestRootPath(fc, "test"));
    //System.out.println("TargetOfTests = "+ targetOfTests.toUri());
    return fc;
  }

};