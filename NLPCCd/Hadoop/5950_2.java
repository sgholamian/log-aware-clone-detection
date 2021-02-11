//,temp,sample_436.java,2,10,temp,sample_4015.java,2,10
//,3
public class xxx {
public static void createDirectory(FileSystem fs, Path dirPath) throws IOException {
fs.delete(dirPath, true);
boolean created = fs.mkdirs(dirPath);
if (!created) {


log.info("could not create directory this might cause test failures");
}
}

};