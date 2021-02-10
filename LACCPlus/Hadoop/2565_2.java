//,temp,TrashPolicyDefault.java,194,202,temp,TrashPolicyDefault.java,184,192
//,3
public class xxx {
  @SuppressWarnings("deprecation")
  public void createCheckpoint(Date date) throws IOException {
    Collection<FileStatus> trashRoots = fs.getTrashRoots(false);
    for (FileStatus trashRoot: trashRoots) {
      LOG.info("TrashPolicyDefault#createCheckpoint for trashRoot: " +
          trashRoot.getPath());
      createCheckpoint(trashRoot.getPath(), date);
    }
  }

};