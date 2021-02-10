//,temp,ViewFsTestSetup.java,57,89,temp,ViewFileSystemTestSetup.java,59,89
//,3
public class xxx {
  static public FileSystem setupForViewFileSystem(Configuration conf, FileSystemTestHelper fileSystemTestHelper, FileSystem fsTarget) throws Exception {
    /**
     * create the test root on local_fs - the  mount table will point here
     */
    Path targetOfTests = fileSystemTestHelper.getTestRootPath(fsTarget);
    // In case previous test was killed before cleanup
    fsTarget.delete(targetOfTests, true);
    fsTarget.mkdirs(targetOfTests);


    // Set up viewfs link for test dir as described above
    String testDir = fileSystemTestHelper.getTestRootPath(fsTarget).toUri()
        .getPath();
    linkUpFirstComponents(conf, testDir, fsTarget, "test dir");
    
    
    // Set up viewfs link for home dir as described above
    setUpHomeDir(conf, fsTarget);
    
    
    // the test path may be relative to working dir - we need to make that work:
    // Set up viewfs link for wd as described above
    String wdDir = fsTarget.getWorkingDirectory().toUri().getPath();
    linkUpFirstComponents(conf, wdDir, fsTarget, "working dir");


    FileSystem fsView = FileSystem.get(FsConstants.VIEWFS_URI, conf);
    fsView.setWorkingDirectory(new Path(wdDir)); // in case testdir relative to wd.
    Log.info("Working dir is: " + fsView.getWorkingDirectory());
    return fsView;
  }

};