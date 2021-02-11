//,temp,sample_1438.java,2,10,temp,sample_66.java,2,13
//,3
public class xxx {
protected void cleanupBulkLoadDirs(FileSystem fs, List<Path> pathList) throws IOException {
for (Path path : pathList) {
if (!fs.delete(path, true)) {


log.info("can t delete");
}
}
}

};