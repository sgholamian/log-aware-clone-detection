//,temp,sample_6921.java,2,13,temp,sample_5053.java,2,12
//,3
public class xxx {
private void deleteTempFile(Path tempPath) {
try {
if (tempPath != null) {
fs.delete(tempPath, false);
}
} catch (IOException ioe) {


log.info("exception received while deleting temp files");
}
}

};