//,temp,TestIntegration.java,140,156,temp,TestIntegration.java,94,109
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