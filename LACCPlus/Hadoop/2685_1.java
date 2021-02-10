//,temp,FsDatasetAsyncDiskService.java,236,242,temp,TestFsShellTouch.java,58,62
//,3
public class xxx {
  void deleteSync(FsVolumeReference volumeRef, ReplicaInfo replicaToDelete,
      ExtendedBlock block, String trashDirectory) {
    LOG.info("Deleting " + block.getLocalBlock() + " replica " + replicaToDelete);
    ReplicaFileDeleteTask deletionTask = new ReplicaFileDeleteTask(volumeRef,
        replicaToDelete, block, trashDirectory);
    deletionTask.run();
  }

};