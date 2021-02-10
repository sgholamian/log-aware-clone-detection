//,temp,FSImage.java,1268,1279,temp,FSImage.java,1220,1236
//,3
public class xxx {
  private void deleteCancelledCheckpoint(long txid) throws IOException {
    ArrayList<StorageDirectory> al = Lists.newArrayList();

    for (StorageDirectory sd : storage.dirIterable(NameNodeDirType.IMAGE)) {
      File ckpt = NNStorage.getStorageFile(sd, NameNodeFile.IMAGE_NEW, txid);
      if (ckpt.exists() && !ckpt.delete()) {
        LOG.warn("Unable to delete cancelled checkpoint in " + sd);
        al.add(sd);            
      }
    }
    storage.reportErrorsOnDirectories(al);
  }

};