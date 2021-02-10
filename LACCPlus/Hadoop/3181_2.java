//,temp,ITestS3AInputStreamPerformance.java,446,473,temp,ITestS3AInputStreamPerformance.java,374,397
//,3
public class xxx {
  protected void executeSeekReadSequence(long blockSize,
      long readahead,
      S3AInputPolicy policy) throws IOException {
    in = openTestFile(policy, readahead);
    long len = testDataStatus.getLen();
    NanoTimer timer = new NanoTimer();
    long blockCount = len / blockSize;
    LOG.info("Reading {} blocks, readahead = {}",
        blockCount, readahead);
    for (long i = 0; i < blockCount; i++) {
      in.seek(in.getPos() + blockSize - 1);
      // this is the read
      assertTrue(in.read() >= 0);
    }
    timer.end("Time to execute %d seeks of distance %d with readahead = %d",
        blockCount,
        blockSize,
        readahead);
    logTimePerIOP("seek(pos + " + blockCount+"); read()", timer, blockCount);
    LOG.info("Effective bandwidth {} MB/S",
        timer.bandwidthDescription(streamStatistics.bytesRead -
            streamStatistics.bytesSkippedOnSeek));
    logStreamStatistics();
  }

};