//,temp,ITestBlockBlobInputStream.java,731,759,temp,ITestBlockBlobInputStream.java,698,724
//,3
public class xxx {
  @Test
  public void test_0316_SequentialReadAfterReverseSeekPerformanceV2()
      throws IOException {
    assumeHugeFileExists();
    final int maxAttempts = 10;
    final double maxAcceptableRatio = 1.01;
    double beforeSeekElapsedMs = 0, afterSeekElapsedMs = 0;
    double ratio = Double.MAX_VALUE;
    for (int i = 0; i < maxAttempts && ratio >= maxAcceptableRatio; i++) {
      beforeSeekElapsedMs = sequentialRead(2,
          accountUsingInputStreamV2.getFileSystem(), false);
      afterSeekElapsedMs = sequentialRead(2,
          accountUsingInputStreamV2.getFileSystem(), true);
      ratio = afterSeekElapsedMs / beforeSeekElapsedMs;
      LOG.info(String.format(
          "beforeSeekElapsedMs=%1$d, afterSeekElapsedMs=%2$d, ratio=%3$.2f",
          (long) beforeSeekElapsedMs,
          (long) afterSeekElapsedMs,
          ratio));
    }
    assertTrue(String.format(
        "Performance of version 2 after reverse seek is not acceptable:"
            + " beforeSeekElapsedMs=%1$d, afterSeekElapsedMs=%2$d,"
            + " ratio=%3$.2f",
        (long) beforeSeekElapsedMs,
        (long) afterSeekElapsedMs,
        ratio),
        ratio < maxAcceptableRatio);
  }

};