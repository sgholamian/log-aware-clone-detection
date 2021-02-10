//,temp,FSImage.java,1268,1279,temp,FSImage.java,1220,1236
//,3
public class xxx {
  private void renameCheckpoint(long txid, NameNodeFile fromNnf,
      NameNodeFile toNnf, boolean renameMD5) throws IOException {
    ArrayList<StorageDirectory> al = null;

    for (StorageDirectory sd : storage.dirIterable(NameNodeDirType.IMAGE)) {
      try {
        renameImageFileInDir(sd, fromNnf, toNnf, txid, renameMD5);
      } catch (IOException ioe) {
        LOG.warn("Unable to rename checkpoint in " + sd, ioe);
        if (al == null) {
          al = Lists.newArrayList();
        }
        al.add(sd);
      }
    }
    if(al != null) storage.reportErrorsOnDirectories(al);
  }

};