//,temp,TestDFSStripedOutputStreamWithFailure.java,273,299,temp,TestDFSStripedOutputStreamWithFailureBase.java,255,274
//,3
public class xxx {
  @Test
  public void runTestWithShortStripe() throws Exception {
    final HdfsConfiguration conf = newHdfsConfiguration();
    // Write a file with a 1 cell partial stripe
    final int length = cellSize - 123;
    // Kill all but one DN
    final int[] dnIndex = new int[dataBlocks + parityBlocks - 1];
    for (int i = 0; i < dnIndex.length; i++) {
      dnIndex[i] = i;
    }
    final int[] killPos = getKillPositions(length, dnIndex.length);

    try {
      LOG.info("runTestWithShortStripe: length==" + length + ", killPos="
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

};