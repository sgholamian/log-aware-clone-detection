//,temp,TestFileChecksum.java,251,264,temp,TestFileChecksum.java,198,215
//,3
public class xxx {
  @Test(timeout = 90000)
  public void testStripedFileChecksumWithMissedDataBlocks1() throws Exception {
    prepareTestFiles(fileSize, new String[] {stripedFile1});
    FileChecksum stripedFileChecksum1 = getFileChecksum(stripedFile1, fileSize,
        false);
    FileChecksum stripedFileChecksumRecon = getFileChecksum(stripedFile1,
        fileSize, true);

    LOG.info("stripedFileChecksum1:" + stripedFileChecksum1);
    LOG.info("stripedFileChecksumRecon:" + stripedFileChecksumRecon);

    Assert.assertTrue("Checksum mismatches!",
        stripedFileChecksum1.equals(stripedFileChecksumRecon));
  }

};