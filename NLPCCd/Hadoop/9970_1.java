//,temp,sample_8630.java,2,16,temp,sample_5931.java,2,16
//,3
public class xxx {
private void mkdir(FileContext fc, Path path, FsPermission fsp) throws IOException {
if (!fc.util().exists(path)) {
try {
fc.mkdir(path, fsp, true);
FileStatus fsStatus = fc.getFileStatus(path);
if (fsStatus.getPermission().toShort() != fsp.toShort()) {
fc.setPermission(path, fsp);
}
} catch (FileAlreadyExistsException e) {


log.info("directory already exists");
}
}
}

};