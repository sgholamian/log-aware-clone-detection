//,temp,TestIntegration.java,141,157,temp,TestIntegration.java,95,110
//,3
public class xxx {
  private void caseSingleFileTargetDir(boolean sync) {

    try {
      addEntries(listFile, "singlefile2/file2");
      createFiles("singlefile2/file2");
      mkdirs(target.toString());

      runTest(listFile, target, true, sync);

      checkResult(target, 1, "file2");
    } catch (IOException e) {
      LOG.error("Exception encountered while testing distcp", e);
      Assert.fail("distcp failure");
    } finally {
      TestDistCpUtils.delete(fs, root);
    }
  }

};