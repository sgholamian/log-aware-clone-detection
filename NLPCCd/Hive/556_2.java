//,temp,sample_4603.java,2,12,temp,sample_4604.java,2,14
//,3
public class xxx {
private void deletePartitionData(List<Path> partPaths, boolean ifPurge) {
if (partPaths != null && !partPaths.isEmpty()) {
for (Path partPath : partPaths) {
try {
wh.deleteDir(partPath, true, ifPurge);
} catch (Exception e) {


log.info("failed to delete partition directory");
}
}
}
}

};