//,temp,sample_3051.java,2,11,temp,sample_3050.java,2,10
//,2
public class xxx {
public static Decompressor getDecompressor(CompressionCodec codec) {
Decompressor decompressor = borrow(DECOMPRESSOR_POOL, codec .getDecompressorType());
if (decompressor == null) {
decompressor = codec.createDecompressor();


log.info("got brand new decompressor");
}
}

};