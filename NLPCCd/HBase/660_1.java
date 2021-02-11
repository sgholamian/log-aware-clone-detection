//,temp,sample_3222.java,2,16,temp,sample_4305.java,2,15
//,3
public class xxx {
public void dummy_method(){
TableName table = TableName.valueOf(args[args.length - 1]);
Configuration conf = new Configuration(util.getConfiguration());
FileSystem fs = FileSystem.get(conf);
Path inputPath = fs.makeQualified(new Path(util .getDataTestDirOnTestFS(table.getNameAsString()), "input.dat"));
FSDataOutputStream op = fs.create(inputPath, true);
if (data == null) {
data = "KEY\u001bVALUE1\u001bVALUE2\n";
}
op.write(Bytes.toBytes(data));
op.close();


log.info("wrote test data to file s");
}

};