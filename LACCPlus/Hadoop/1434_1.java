//,temp,TestDistCpViewFs.java,105,120,temp,TestIntegration.java,226,242
//,3
public class xxx {
  private void caseSingleFileTargetFile(boolean sync) {

    try {
      addEntries(listFile, "singlefile1/file1");
      createFiles("singlefile1/file1", target.toString());

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