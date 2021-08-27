//,temp,sample_4085.java,2,10,temp,sample_4719.java,2,10
//,2
public class xxx {
private static void tryDelete(FileSystem fs, Path path) {
try {
fs.delete(path, true);
} catch (IOException ex) {


log.info("failed to delete");
}
}

};