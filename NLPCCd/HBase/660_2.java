//,temp,sample_3222.java,2,16,temp,sample_4305.java,2,15
//,3
public class xxx {
private Tool doMROnTableTest(HBaseTestingUtility util, String family, String data, String[] args, int valueMultiplier, boolean dataAvailable) throws Exception {
String table = args[args.length - 1];
Configuration conf = new Configuration(util.getConfiguration());
FileSystem fs = FileSystem.get(conf);
Path inputPath = fs.makeQualified(new Path(util.getDataTestDirOnTestFS(table), "input.dat"));
FSDataOutputStream op = fs.create(inputPath, true);
op.write(Bytes.toBytes(data));
op.close();
if (conf.getBoolean(FORCE_COMBINER_CONF, true)) {


log.info("forcing combiner");
}
}

};