//,temp,sample_1495.java,2,14,temp,sample_1498.java,2,14
//,2
public class xxx {
public Compressor getCompressor() {
CompressionCodec codec = getCodec(conf);
if (codec != null) {
Compressor compressor = CodecPool.getCompressor(codec);
if (compressor != null) {
if (compressor.finished()) {


log.info("compressor obtained from codecpool is already finished");
}
}
}
}

};