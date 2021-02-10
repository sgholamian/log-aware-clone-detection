//,temp,TestCodec.java,468,481,temp,TestCodec.java,448,466
//,3
public class xxx {
  @Test
  public void testCodecInitWithCompressionLevel() throws Exception {
    Configuration conf = new Configuration();
    conf.setBoolean(CommonConfigurationKeys.IO_NATIVE_LIB_AVAILABLE_KEY, true);
    if (ZlibFactory.isNativeZlibLoaded(conf)) {
      LOG.info("testCodecInitWithCompressionLevel with native");
      codecTestWithNOCompression(conf,
                            "org.apache.hadoop.io.compress.GzipCodec");
      codecTestWithNOCompression(conf,
                         "org.apache.hadoop.io.compress.DefaultCodec");
    } else {
      LOG.warn("testCodecInitWithCompressionLevel for native skipped"
               + ": native libs not loaded");
    }
    conf = new Configuration();
    conf.setBoolean(CommonConfigurationKeys.IO_NATIVE_LIB_AVAILABLE_KEY, false);
    codecTestWithNOCompression( conf,
                         "org.apache.hadoop.io.compress.DefaultCodec");
  }

};