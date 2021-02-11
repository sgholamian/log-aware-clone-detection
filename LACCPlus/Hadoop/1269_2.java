//,temp,TestIntegration.java,441,463,temp,TestIntegration.java,250,265
//,3
public class xxx {
  private void caseMultiFileTargetMissing(boolean sync) {

    try {
      addEntries(listFile, "multifile/file3", "multifile/file4", "multifile/file5");
      createFiles("multifile/file3", "multifile/file4", "multifile/file5");

      runTest(listFile, target, false, sync);

      checkResult(target, 3, "file3", "file4", "file5");
    } catch (IOException e) {
      LOG.error("Exception encountered while testing distcp", e);
      Assert.fail("distcp failure");
    } finally {
      TestDistCpUtils.delete(fs, root);
    }
  }

};