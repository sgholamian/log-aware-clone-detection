//,temp,TestIntegration.java,140,156,temp,TestIntegration.java,94,109
//,3
public class xxx {
  private void caseSingleFileMissingTarget(boolean sync) {

    try {
      addEntries(listFile, "singlefile1/file1");
      createFiles("singlefile1/file1");

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