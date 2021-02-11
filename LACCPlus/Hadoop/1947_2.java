//,temp,TestDistCpViewFs.java,213,229,temp,TestIntegration.java,344,362
//,3
public class xxx {
  @Test(timeout=100000)
  public void testDeleteMissingInDestination() {
    
    try {
      addEntries(listFile, "srcdir");
      createFiles("srcdir/file1", "dstdir/file1", "dstdir/file2");
      
      Path target = new Path(root + "/dstdir");
      runTest(listFile, target, false, true, true, false);
      
      checkResult(target, 1, "file1");
    } catch (IOException e) {
      LOG.error("Exception encountered while running distcp", e);
      Assert.fail("distcp failure");
    } finally {
      TestDistCpUtils.delete(fs, root);
      TestDistCpUtils.delete(fs, "target/tmp1");
    }
  }

};