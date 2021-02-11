//,temp,sample_7703.java,2,11,temp,sample_5768.java,2,11
//,3
public class xxx {
private void delete(SwiftNativeFileSystem fs, Path path) {
try {
if (!fs.delete(path, false)) {
}
} catch (IOException e) {


log.info("deleting");
}
}

};