//,temp,sample_67.java,2,18,temp,sample_3875.java,2,12
//,3
public class xxx {
private void deletePath(Path path) {
try {
if (path != null) {
fs.delete(path, true);
}
} catch (IOException e) {


log.info("failed to delete the file");
}
}

};