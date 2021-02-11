//,temp,TestCodec.java,468,481,temp,TestCodec.java,448,466
//,3
public class xxx {
  @Test
  public void testCodecPoolCompressorReinit() throws Exception {
    Configuration conf = new Configuration();
    conf.setBoolean(CommonConfigurationKeys.IO_NATIVE_LIB_AVAILABLE_KEY, true);
    if (ZlibFactory.isNativeZlibLoaded(conf)) {
      GzipCodec gzc = ReflectionUtils.newInstance(GzipCodec.class, conf);
      gzipReinitTest(conf, gzc);
    } else {
      LOG.warn("testCodecPoolCompressorReinit skipped: native libs not loaded");
    }
    conf.setBoolean(CommonConfigurationKeys.IO_NATIVE_LIB_AVAILABLE_KEY, false);
    DefaultCodec dfc = ReflectionUtils.newInstance(DefaultCodec.class, conf);
    gzipReinitTest(conf, dfc);
  }

};