//,temp,TestDistCpViewFs.java,376,398,temp,TestDistCpViewFs.java,213,229
//,3
public class xxx {
  private void caseMultiFileTargetPresent(boolean sync) {

    try {
      addEntries(listFile, "multifile/file3", "multifile/file4", "multifile/file5");
      createFiles("multifile/file3", "multifile/file4", "multifile/file5");
      mkdirs(target.toString());

      runTest(listFile, target, true, sync);

      checkResult(target, 3, "file3", "file4", "file5");
    } catch (IOException e) {
      LOG.error("Exception encountered while testing distcp", e);
      Assert.fail("distcp failure");
    } finally {
      TestDistCpUtils.delete(fs, root);
    }
  }

};