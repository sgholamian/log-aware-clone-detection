//,temp,sample_4821.java,2,14,temp,sample_9605.java,2,13
//,3
public class xxx {
private Path getAppRootDir(String user) throws IOException {
if (!storeInsideUserDir) {
return activePath;
}
Path userDir = new Path(activePath, user);
if (FileSystem.mkdirs(fs, userDir, new FsPermission(APP_LOG_DIR_PERMISSIONS))) {
if (LOG.isDebugEnabled()) {


log.info("new user directory created");
}
}
}

};