//,temp,CodecPool.java,121,131,temp,CodecPool.java,100,109
//,2
public class xxx {
  public static Compressor getCompressor(CompressionCodec codec) {
    Compressor compressor = borrow(COMPRESSOR_POOL, codec.getCompressorType());
    if (compressor == null) {
      compressor = codec.createCompressor();
      LOG.info("Got brand-new compressor");
    } else {
      LOG.debug("Got recycled compressor");
    }
    return compressor;
  }

};