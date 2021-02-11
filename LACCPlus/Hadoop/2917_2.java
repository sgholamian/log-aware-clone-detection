//,temp,TestFileChecksum.java,251,264,temp,TestFileChecksum.java,198,215
//,3
public class xxx {
  private void testStripedFileChecksum(int range1, int range2)
      throws Exception {
    FileChecksum stripedFileChecksum1 = getFileChecksum(stripedFile1,
        range1, false);
    FileChecksum stripedFileChecksum2 = getFileChecksum(stripedFile2,
        range1, false);
    FileChecksum stripedFileChecksum3 = getFileChecksum(stripedFile2,
        range2, false);

    LOG.info("stripedFileChecksum1:" + stripedFileChecksum1);
    LOG.info("stripedFileChecksum2:" + stripedFileChecksum2);
    LOG.info("stripedFileChecksum3:" + stripedFileChecksum3);

    Assert.assertTrue(stripedFileChecksum1.equals(stripedFileChecksum2));
    if (range1 >=0 && range1 != range2) {
      Assert.assertFalse(stripedFileChecksum1.equals(stripedFileChecksum3));
    }
  }

};