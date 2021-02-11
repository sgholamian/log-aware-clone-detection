//,temp,TestDFSStripedOutputStreamWithFailureBase.java,255,274,temp,TestDFSStripedOutputStreamWithFailureBase.java,237,253
//,3
public class xxx {
  void runTest(final int length) {
    final HdfsConfiguration conf = newHdfsConfiguration();
    for (int dn = 0; dn < dataBlocks + parityBlocks; dn++) {
      try {
        LOG.info("runTest: dn=" + dn + ", length=" + length);
        setup(conf);
        runTest(length, new int[]{length / 2}, new int[]{dn}, false);
      } catch (Throwable e) {
        final String err = "failed, dn=" + dn + ", length=" + length
            + StringUtils.stringifyException(e);
        LOG.error(err);
        Assert.fail(err);
      } finally {
        tearDown();
      }
    }
  }

};