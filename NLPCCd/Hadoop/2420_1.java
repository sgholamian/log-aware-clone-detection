//,temp,sample_7973.java,2,9,temp,sample_4201.java,2,12
//,3
public class xxx {
private static void sequenceFileCodecTest(Configuration conf, int lines, String codecClass, int blockSize) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
Path filePath = new Path("SequenceFileCodecTest." + codecClass);
conf.setInt("io.seqfile.compress.blocksize", blockSize);
FileSystem fs = FileSystem.get(conf);


log.info("creating sequencefile with codec codecclass");
}

};