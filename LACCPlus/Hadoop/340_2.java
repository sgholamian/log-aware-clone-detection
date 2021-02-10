//,temp,TestDistCpViewFs.java,237,252,temp,TestIntegration.java,118,133
//,3
public class xxx {
  private void caseSingleFileTargetFile(boolean sync) {

    try {
      addEntries(listFile, "singlefile1/file1");
      createFiles("singlefile1/file1", "target");

      runTest(listFile, target, false, sync);

      checkResult(target, 1);
    } catch (IOException e) {
      LOG.error("Exception encountered while testing distcp", e);
      Assert.fail("distcp failure");
    } finally {
      TestDistCpUtils.delete(fs, root);
    }
  }

};