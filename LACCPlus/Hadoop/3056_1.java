//,temp,ResourceLocalizationService.java,604,616,temp,TestSwiftFileSystemBasicOps.java,119,127
//,3
public class xxx {
  private void submitDirForDeletion(String userName, Path dir) {
    try {
      lfs.getFileStatus(dir);
      FileDeletionTask deletionTask = new FileDeletionTask(delService, userName,
          dir, null);
      delService.delete(deletionTask);
    } catch (UnsupportedFileSystemException ue) {
      LOG.warn("Local dir " + dir + " is an unsupported filesystem", ue);
    } catch (IOException ie) {
      // ignore
      return;
    }
  }

};