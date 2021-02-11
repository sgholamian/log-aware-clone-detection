//,temp,TestWebHdfsFileSystemContract.java,188,198,temp,TestToken.java,108,119
//,3
public class xxx {
  @Test
  public void testDecodeWritableArgSanityCheck() throws Exception {
    Token<AbstractDelegationTokenIdentifier> token =
            new Token<AbstractDelegationTokenIdentifier>();
    try {
      token.decodeFromUrlString(null);
      fail("Should have thrown HadoopIllegalArgumentException");
    }
    catch (HadoopIllegalArgumentException e) {
      Token.LOG.info("Test decodeWritable() sanity check success.");
    }
  }

};