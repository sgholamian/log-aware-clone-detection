//,temp,FsDatasetAsyncDiskService.java,281,312,temp,FsDatasetImpl.java,867,890
//,3
public class xxx {
    private boolean moveFiles() {
      if (trashDirectory == null) {
        LOG.error("Trash dir for replica " + replicaToDelete + " is null");
        return false;
      }

      File trashDirFile = new File(trashDirectory);
      try {
        volume.getFileIoProvider().mkdirsWithExistsCheck(
            volume, trashDirFile);
      } catch (IOException e) {
        return false;
      }

      if (LOG.isDebugEnabled()) {
        LOG.debug("Moving files " + replicaToDelete.getBlockURI() + " and " +
            replicaToDelete.getMetadataURI() + " to trash.");
      }

      final String blockName = replicaToDelete.getBlockName();
      final long genstamp = replicaToDelete.getGenerationStamp();
      File newBlockFile = new File(trashDirectory, blockName);
      File newMetaFile = new File(trashDirectory,
          DatanodeUtil.getMetaName(blockName, genstamp));
      try {
        return (replicaToDelete.renameData(newBlockFile.toURI()) &&
                replicaToDelete.renameMeta(newMetaFile.toURI()));
      } catch (IOException e) {
        LOG.error("Error moving files to trash: " + replicaToDelete, e);
      }
      return false;
    }

};