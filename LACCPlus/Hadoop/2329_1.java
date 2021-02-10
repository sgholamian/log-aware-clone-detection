//,temp,HistoryFileManager.java,1110,1131,temp,HistoryFileManager.java,751,769
//,3
public class xxx {
  private void makeDoneSubdir(Path path) throws IOException {
    try {
      doneDirFc.getFileStatus(path);
      existingDoneSubdirs.add(path);
    } catch (FileNotFoundException fnfE) {
      try {
        FsPermission fsp = new FsPermission(
            JobHistoryUtils.HISTORY_DONE_DIR_PERMISSION);
        doneDirFc.mkdir(path, fsp, true);
        FileStatus fsStatus = doneDirFc.getFileStatus(path);
        LOG.info("Perms after creating " + fsStatus.getPermission().toShort()
            + ", Expected: " + fsp.toShort());
        if (fsStatus.getPermission().toShort() != fsp.toShort()) {
          LOG.info("Explicitly setting permissions to : " + fsp.toShort()
              + ", " + fsp);
          doneDirFc.setPermission(path, fsp);
        }
        existingDoneSubdirs.add(path);
      } catch (FileAlreadyExistsException faeE) { // Nothing to do.
      }
    }
  }

};