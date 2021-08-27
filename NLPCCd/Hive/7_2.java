//,temp,sample_3049.java,2,11,temp,sample_3048.java,2,10
//,2
public class xxx {
public static Compressor getCompressor(CompressionCodec codec) {
Compressor compressor = borrow(COMPRESSOR_POOL, codec.getCompressorType());
if (compressor == null) {
compressor = codec.createCompressor();


log.info("got brand new compressor");
}
}

};