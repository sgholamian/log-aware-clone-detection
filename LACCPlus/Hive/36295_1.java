//,temp,CodecPool.java,121,131,temp,CodecPool.java,100,109
//,2
public class xxx {
  public static Decompressor getDecompressor(CompressionCodec codec) {
    Decompressor decompressor = borrow(DECOMPRESSOR_POOL, codec
        .getDecompressorType());
    if (decompressor == null) {
      decompressor = codec.createDecompressor();
      LOG.info("Got brand-new decompressor");
    } else {
      LOG.debug("Got recycled decompressor");
    }
    return decompressor;
  }

};