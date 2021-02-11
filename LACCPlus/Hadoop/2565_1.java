//,temp,TrashPolicyDefault.java,194,202,temp,TrashPolicyDefault.java,184,192
//,3
public class xxx {
  @Override
  public void deleteCheckpoint() throws IOException {
    Collection<FileStatus> trashRoots = fs.getTrashRoots(false);
    for (FileStatus trashRoot : trashRoots) {
      LOG.info("TrashPolicyDefault#deleteCheckpoint for trashRoot: " +
          trashRoot.getPath());
      deleteCheckpoint(trashRoot.getPath());
    }
  }

};