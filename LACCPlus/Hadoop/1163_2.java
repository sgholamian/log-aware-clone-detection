//,temp,TestCryptoCodec.java,90,107,temp,TestCryptoCodec.java,71,88
//,2
public class xxx {
  @Test(timeout=120000)
  public void testJceAesCtrCryptoCodec() throws Exception {
    GenericTestUtils.assumeInNativeProfile();
    if (!NativeCodeLoader.buildSupportsOpenssl()) {
      LOG.warn("Skipping test since openSSL library not loaded");
      Assume.assumeTrue(false);
    }
    Assert.assertEquals(null, OpensslCipher.getLoadingFailureReason());
    cryptoCodecTest(conf, seed, 0, jceCodecClass, jceCodecClass, iv);
    cryptoCodecTest(conf, seed, count, jceCodecClass, jceCodecClass, iv);
    cryptoCodecTest(conf, seed, count, jceCodecClass, opensslCodecClass, iv);
    // Overflow test, IV: xx xx xx xx xx xx xx xx ff ff ff ff ff ff ff ff 
    for(int i = 0; i < 8; i++) {
      iv[8 + i] = (byte) 0xff;
    }
    cryptoCodecTest(conf, seed, count, jceCodecClass, jceCodecClass, iv);
    cryptoCodecTest(conf, seed, count, jceCodecClass, opensslCodecClass, iv);
  }

};