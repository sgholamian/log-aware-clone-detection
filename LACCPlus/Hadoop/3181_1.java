//,temp,ITestS3AInputStreamPerformance.java,446,473,temp,ITestS3AInputStreamPerformance.java,374,397
//,3
public class xxx {
  private ContractTestUtils.NanoTimer executeRandomIO(S3AInputPolicy policy,
      long expectedOpenCount)
      throws IOException {
    describe("Random IO with policy \"%s\"", policy);
    byte[] buffer = new byte[_1MB];
    long totalBytesRead = 0;

    in = openTestFile(policy, 0);
    ContractTestUtils.NanoTimer timer = new ContractTestUtils.NanoTimer();
    for (int[] action : RANDOM_IO_SEQUENCE) {
      int position = action[0];
      int range = action[1];
      in.readFully(position, buffer, 0, range);
      totalBytesRead += range;
    }
    int reads = RANDOM_IO_SEQUENCE.length;
    timer.end("Time to execute %d reads of total size %d bytes",
        reads,
        totalBytesRead);
    in.close();
    assertOpenOperationCount(expectedOpenCount);
    logTimePerIOP("byte read", timer, totalBytesRead);
    LOG.info("Effective bandwidth {} MB/S",
        timer.bandwidthDescription(streamStatistics.bytesRead -
            streamStatistics.bytesSkippedOnSeek));
    logStreamStatistics();
    return timer;
  }

};