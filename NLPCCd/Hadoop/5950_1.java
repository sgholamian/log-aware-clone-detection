//,temp,sample_436.java,2,10,temp,sample_4015.java,2,10
//,3
public class xxx {
private void deleteCorruptedFile(String path) {
try {
namenode.getRpcServer().delete(path, true);
} catch (Exception e) {


log.info("fsck error deleting corrupted file");
}
}

};