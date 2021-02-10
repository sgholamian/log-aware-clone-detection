//,temp,TestDFSStripedOutputStreamWithFailure.java,273,299,temp,TestDFSStripedOutputStreamWithFailureBase.java,255,274
//,3
public class xxx {
  void runTestWithMultipleFailure(final int length) throws Exception {
    final HdfsConfiguration conf = newHdfsConfiguration();
    for (int[] dnIndex : dnIndexSuite) {
      int[] killPos = getKillPositions(length, dnIndex.length);
      try {
        LOG.info("runTestWithMultipleFailure: length==" + length + ", killPos="
            + Arrays.toString(killPos) + ", dnIndex="
            + Arrays.toString(dnIndex));
        setup(conf);
        runTest(length, killPos, dnIndex, false);
      } catch (Throwable e) {
        final String err = "failed, killPos=" + Arrays.toString(killPos)
            + ", dnIndex=" + Arrays.toString(dnIndex) + ", length=" + length;
        LOG.error(err);
        throw e;
      } finally {
        tearDown();
      }
    }
  }

};