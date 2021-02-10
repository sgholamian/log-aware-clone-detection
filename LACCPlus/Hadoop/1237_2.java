//,temp,FsDatasetImpl.java,1266,1285,temp,FsDatasetImpl.java,824,846
//,3
public class xxx {
  static File moveBlockFiles(Block b, File srcfile, File destdir)
      throws IOException {
    final File dstfile = new File(destdir, b.getBlockName());
    final File srcmeta = FsDatasetUtil.getMetaFile(srcfile, b.getGenerationStamp());
    final File dstmeta = FsDatasetUtil.getMetaFile(dstfile, b.getGenerationStamp());
    try {
      NativeIO.renameTo(srcmeta, dstmeta);
    } catch (IOException e) {
      throw new IOException("Failed to move meta file for " + b
          + " from " + srcmeta + " to " + dstmeta, e);
    }
    try {
      NativeIO.renameTo(srcfile, dstfile);
    } catch (IOException e) {
      throw new IOException("Failed to move block file for " + b
          + " from " + srcfile + " to " + dstfile.getAbsolutePath(), e);
    }
    if (LOG.isDebugEnabled()) {
      LOG.debug("addFinalizedBlock: Moved " + srcmeta + " to " + dstmeta
          + " and " + srcfile + " to " + dstfile);
    }
    return dstfile;
  }

};