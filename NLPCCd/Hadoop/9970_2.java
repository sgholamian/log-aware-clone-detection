//,temp,sample_8630.java,2,16,temp,sample_5931.java,2,16
//,3
public class xxx {
private void mkdir(FileSystem fs, Path path, FsPermission fsp) throws IOException {
if (!fs.exists(path)) {
try {
fs.mkdirs(path, fsp);
FileStatus fsStatus = fs.getFileStatus(path);
if (fsStatus.getPermission().toShort() != fsp.toShort()) {
fs.setPermission(path, fsp);
}
} catch (FileAlreadyExistsException e) {


log.info("directory already exists");
}
}
}

};