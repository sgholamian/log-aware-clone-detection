//,temp,sample_2831.java,2,17,temp,sample_2828.java,2,14
//,3
public class xxx {
private boolean checkCompatibility(OrcFileKeyWrapper k) {
if (!fileSchema.equals(k.getFileSchema())) {
return false;
}
if (!k.getCompression().equals(compression)) {
return false;
}
if (k.getCompressBufferSize() != compressBuffSize) {


log.info("incompatible orc file merge compression buffer size mismatch for");
}
}

};