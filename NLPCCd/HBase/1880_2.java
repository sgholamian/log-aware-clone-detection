//,temp,sample_1495.java,2,14,temp,sample_1498.java,2,14
//,2
public class xxx {
public Decompressor getDecompressor() {
CompressionCodec codec = getCodec(conf);
if (codec != null) {
Decompressor decompressor = CodecPool.getDecompressor(codec);
if (decompressor != null) {
if (decompressor.finished()) {


log.info("deompressor obtained from codecpool is already finished");
}
}
}
}

};