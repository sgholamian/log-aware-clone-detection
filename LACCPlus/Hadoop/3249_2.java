//,temp,ITestS3AInputStreamPerformance.java,207,270,temp,ITestAzureHugeFiles.java,344,413
//,3
public class xxx {
  @Test
  public void test_060_openAndReadWholeFileBlocks() throws Throwable {
    FileStatus status = assumeHugeFileExists();
    int blockSize = S_1M;
    describe("Open the test file and read it in blocks of size %d",
        blockSize);
    long len =  status.getLen();
    FSDataInputStream in = openDataFile();
    NanoTimer timer2 = null;
    long blockCount = 0;
    long totalToRead = 0;
    int resetCount = 0;
    try {
      byte[] block = new byte[blockSize];
      timer2 = new NanoTimer();
      long count = 0;
      // implicitly rounding down here
      blockCount = len / blockSize;
      totalToRead = blockCount * blockSize;
      long minimumBandwidth = S_128K;
      int maxResetCount = 4;
      resetCount = 0;
      for (long i = 0; i < blockCount; i++) {
        int offset = 0;
        int remaining = blockSize;
        long blockId = i + 1;
        NanoTimer blockTimer = new NanoTimer();
        int reads = 0;
        while (remaining > 0) {
          NanoTimer readTimer = new NanoTimer();
          int bytesRead = in.read(block, offset, remaining);
          reads++;
          if (bytesRead == 1) {
            break;
          }
          remaining -= bytesRead;
          offset += bytesRead;
          count += bytesRead;
          readTimer.end();
          if (bytesRead != 0) {
            LOG.debug("Bytes in read #{}: {} , block bytes: {},"
                    + " remaining in block: {}"
                    + " duration={} nS; ns/byte: {}, bandwidth={} MB/s",
                reads, bytesRead, blockSize - remaining, remaining,
                readTimer.duration(),
                readTimer.nanosPerOperation(bytesRead),
                readTimer.bandwidthDescription(bytesRead));
          } else {
            LOG.warn("0 bytes returned by read() operation #{}", reads);
          }
        }
        blockTimer.end("Reading block %d in %d reads", blockId, reads);
        String bw = blockTimer.bandwidthDescription(blockSize);
        LOG.info("Bandwidth of block {}: {} MB/s: ", blockId, bw);
        if (bandwidthInBytes(blockTimer, blockSize) < minimumBandwidth) {
          LOG.warn("Bandwidth {} too low on block {}: resetting connection",
              bw, blockId);
          Assert.assertTrue("Bandwidth of " + bw + " too low after "
              + resetCount + " attempts", resetCount <= maxResetCount);
          resetCount++;
          // reset the connection
        }
      }
    } finally {
      IOUtils.closeStream(in);
    }
    timer2.end("Time to read %d bytes in %d blocks", totalToRead, blockCount);
    LOG.info("Overall Bandwidth {} MB/s; reset connections {}",
        timer2.bandwidth(totalToRead), resetCount);
  }

};