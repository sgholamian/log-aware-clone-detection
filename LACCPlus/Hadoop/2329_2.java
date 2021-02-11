//,temp,HistoryFileManager.java,1110,1131,temp,HistoryFileManager.java,751,769
//,3
public class xxx {
  private void mkdir(FileContext fc, Path path, FsPermission fsp)
      throws IOException {
    if (!fc.util().exists(path)) {
      try {
        fc.mkdir(path, fsp, true);

        FileStatus fsStatus = fc.getFileStatus(path);
        LOG.info("Perms after creating " + fsStatus.getPermission().toShort()
            + ", Expected: " + fsp.toShort());
        if (fsStatus.getPermission().toShort() != fsp.toShort()) {
          LOG.info("Explicitly setting permissions to : " + fsp.toShort()
              + ", " + fsp);
          fc.setPermission(path, fsp);
        }
      } catch (FileAlreadyExistsException e) {
        LOG.info("Directory: [" + path + "] already exists.");
      }
    }
  }

};