//,temp,sample_1248.java,2,14,temp,sample_8709.java,2,14
//,3
public class xxx {
public static void setup() {
try {
fs = FileSystem.get(getConf());
listFile = new Path("target/tmp/listing").makeQualified(fs.getUri(), fs.getWorkingDirectory());
target = new Path("target/tmp/target").makeQualified(fs.getUri(), fs.getWorkingDirectory());
root = new Path("target/tmp").makeQualified(fs.getUri(), fs.getWorkingDirectory()).toString();
TestDistCpUtils.delete(fs, root);
} catch (IOException e) {


log.info("exception encountered");
}
}

};