//,temp,sample_1703.java,2,14,temp,sample_3388.java,2,9
//,3
public class xxx {
private List<Path> getAllFiles(FileSystem fs, Path dir) throws IOException {
FileStatus[] files = FSUtils.listStatus(fs, dir, null);
if (files == null) {


log.info("no files under");
}
}

};