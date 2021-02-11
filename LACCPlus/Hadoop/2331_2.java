//,temp,ITestBlockBlobInputStream.java,731,759,temp,ITestBlockBlobInputStream.java,698,724
//,3
public class xxx {
  @Test
  public void test_0315_SequentialReadPerformance() throws IOException {
    assumeHugeFileExists();
    final int maxAttempts = 10;
    final double maxAcceptableRatio = 1.01;
    double v1ElapsedMs = 0, v2ElapsedMs = 0;
    double ratio = Double.MAX_VALUE;
    for (int i = 0; i < maxAttempts && ratio >= maxAcceptableRatio; i++) {
      v1ElapsedMs = sequentialRead(1,
          accountUsingInputStreamV1.getFileSystem(), false);
      v2ElapsedMs = sequentialRead(2,
          accountUsingInputStreamV2.getFileSystem(), false);
      ratio = v2ElapsedMs / v1ElapsedMs;
      LOG.info(String.format(
          "v1ElapsedMs=%1$d, v2ElapsedMs=%2$d, ratio=%3$.2f",
          (long) v1ElapsedMs,
          (long) v2ElapsedMs,
          ratio));
    }
    assertTrue(String.format(
        "Performance of version 2 is not acceptable: v1ElapsedMs=%1$d,"
            + " v2ElapsedMs=%2$d, ratio=%3$.2f",
        (long) v1ElapsedMs,
        (long) v2ElapsedMs,
        ratio),
        ratio < maxAcceptableRatio);
  }

};