//,temp,ITestAzureHugeFiles.java,415,441,temp,AbstractSTestS3AHugeFiles.java,460,486
//,2
public class xxx {
  @Test
  public void test_100_renameHugeFile() throws Throwable {
    assumeHugeFileExists();
    describe("renaming %s to %s", hugefile, hugefileRenamed);
    S3AFileSystem fs = getFileSystem();
    FileStatus status = fs.getFileStatus(hugefile);
    long size = status.getLen();
    fs.delete(hugefileRenamed, false);
    ContractTestUtils.NanoTimer timer = new ContractTestUtils.NanoTimer();
    fs.rename(hugefile, hugefileRenamed);
    long mb = Math.max(size / _1MB, 1);
    timer.end("time to rename file of %d MB", mb);
    LOG.info("Time per MB to rename = {} nS",
        toHuman(timer.nanosPerOperation(mb)));
    bandwidth(timer, size);
    logFSState();
    FileStatus destFileStatus = fs.getFileStatus(hugefileRenamed);
    assertEquals(size, destFileStatus.getLen());

    // rename back
    ContractTestUtils.NanoTimer timer2 = new ContractTestUtils.NanoTimer();
    fs.rename(hugefileRenamed, hugefile);
    timer2.end("Renaming back");
    LOG.info("Time per MB to rename = {} nS",
        toHuman(timer2.nanosPerOperation(mb)));
    bandwidth(timer2, size);
  }

};