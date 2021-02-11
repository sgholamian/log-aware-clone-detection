//,temp,sample_5386.java,2,11,temp,sample_7307.java,2,12
//,3
public class xxx {
private void resetStateTest(Configuration conf, int seed, int count, String codecClass) throws IOException {
CompressionCodec codec = null;
try {
codec = (CompressionCodec) ReflectionUtils.newInstance(conf .getClassByName(codecClass), conf);
} catch (ClassNotFoundException cnfe) {
throw new IOException("Illegal codec!");
}


log.info("created a codec object of type");
}

};