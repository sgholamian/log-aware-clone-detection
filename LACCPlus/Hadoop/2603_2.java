//,temp,NMTokenSecretManagerInNM.java,249,254,temp,PBImageTextWriter.java,638,644
//,3
public class xxx {
  static void ignoreSnapshotName(long inode) throws IOException {
    // Ignore snapshots - we want the output similar to -ls -R.
    if (LOG.isDebugEnabled()) {
      LOG.debug("No snapshot name found for inode {}", inode);
    }
    throw new IgnoreSnapshotException();
  }

};