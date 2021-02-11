//,temp,NNUpgradeUtil.java,170,191,temp,NNUpgradeUtil.java,84,99
//,3
public class xxx {
  public static void doUpgrade(StorageDirectory sd, Storage storage)
      throws IOException {
    LOG.info("Performing upgrade of storage directory " + sd.getRoot());
    try {
      // Write the version file, since saveFsImage only makes the
      // fsimage_<txid>, and the directory is otherwise empty.
      storage.writeProperties(sd);

      File prevDir = sd.getPreviousDir();
      File tmpDir = sd.getPreviousTmp();
      Preconditions.checkState(!prevDir.exists(),
          "previous directory must not exist for upgrade.");
      Preconditions.checkState(tmpDir.exists(),
          "previous.tmp directory must exist for upgrade.");

      // rename tmp to previous
      NNStorage.rename(tmpDir, prevDir);
    } catch (IOException ioe) {
      LOG.error("Unable to rename temp to previous for " + sd.getRoot(), ioe);
      throw ioe;
    }
  }

};