//,temp,ITestBlockBlobInputStream.java,799,825,temp,ITestBlockBlobInputStream.java,698,724
//,3
public class xxx {
  @Test
  public void test_0317_RandomReadPerformance() throws IOException {
    assumeHugeFileExists();
    final int maxAttempts = 10;
    final double maxAcceptableRatio = 0.10;
    double v1ElapsedMs = 0, v2ElapsedMs = 0;
    double ratio = Double.MAX_VALUE;
    for (int i = 0; i < maxAttempts && ratio >= maxAcceptableRatio; i++) {
      v1ElapsedMs = randomRead(1,
          accountUsingInputStreamV1.getFileSystem());
      v2ElapsedMs = randomRead(2,
          accountUsingInputStreamV2.getFileSystem());
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