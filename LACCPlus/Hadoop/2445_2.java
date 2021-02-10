//,temp,FsDatasetAsyncDiskService.java,281,312,temp,FsDatasetImpl.java,867,890
//,3
public class xxx {
  static File moveBlockFiles(Block b, ReplicaInfo replicaInfo, File destdir)
      throws IOException {
    final File dstfile = new File(destdir, b.getBlockName());
    final File dstmeta = FsDatasetUtil.getMetaFile(dstfile, b.getGenerationStamp());
    try {
      replicaInfo.renameMeta(dstmeta.toURI());
    } catch (IOException e) {
      throw new IOException("Failed to move meta file for " + b
          + " from " + replicaInfo.getMetadataURI() + " to " + dstmeta, e);
    }
    try {
      replicaInfo.renameData(dstfile.toURI());
    } catch (IOException e) {
      throw new IOException("Failed to move block file for " + b
          + " from " + replicaInfo.getBlockURI() + " to "
          + dstfile.getAbsolutePath(), e);
    }
    if (LOG.isDebugEnabled()) {
      LOG.debug("addFinalizedBlock: Moved " + replicaInfo.getMetadataURI()
          + " to " + dstmeta + " and " + replicaInfo.getBlockURI()
          + " to " + dstfile);
    }
    return dstfile;
  }

};