//,temp,sample_2435.java,2,11,temp,sample_2192.java,2,16
//,3
public class xxx {
public void restoreSnapshots(Configuration conf, Map<String, Path> snapshotToDir, FileSystem fs) throws IOException {
Path rootDir = FSUtils.getRootDir(conf);
for (Map.Entry<String, Path> entry : snapshotToDir.entrySet()) {
String snapshotName = entry.getKey();
Path restoreDir = entry.getValue();


log.info("restoring snapshot into for multitablesnapshotinputformat");
}
}

};