//,temp,JobHistoryEventHandler.java,328,345,temp,HistoryFileManager.java,751,769
//,3
public class xxx {
  private void mkdir(FileSystem fs, Path path, FsPermission fsp)
      throws IOException {
    if (!fs.exists(path)) {
      try {
        fs.mkdirs(path, fsp);
        FileStatus fsStatus = fs.getFileStatus(path);
        LOG.info("Perms after creating " + fsStatus.getPermission().toShort()
            + ", Expected: " + fsp.toShort());
        if (fsStatus.getPermission().toShort() != fsp.toShort()) {
          LOG.info("Explicitly setting permissions to : " + fsp.toShort()
              + ", " + fsp);
          fs.setPermission(path, fsp);
        }
      } catch (FileAlreadyExistsException e) {
        LOG.info("Directory: [" + path + "] already exists.");
      }
    }
  }

};