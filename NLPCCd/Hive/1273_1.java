//,temp,sample_2827.java,2,11,temp,sample_2829.java,2,17
//,3
public class xxx {
private boolean checkCompatibility(OrcFileKeyWrapper k) {
if (!fileSchema.equals(k.getFileSchema())) {
return false;
}
if (!k.getCompression().equals(compression)) {


log.info("incompatible orc file merge compression codec mismatch for");
}
}

};