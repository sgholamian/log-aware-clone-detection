//,temp,sample_4085.java,2,10,temp,sample_4719.java,2,10
//,2
public class xxx {
private void delete(Path operatorOutputPath, FileSystem fs) {
try {
fs.delete(operatorOutputPath, true);
} catch (IOException e) {


log.info("failed to delete path");
}
}

};