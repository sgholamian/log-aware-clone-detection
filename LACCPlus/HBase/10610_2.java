//,temp,MobUtils.java,763,777,temp,HMobStore.java,283,297
//,3
public class xxx {
  private void validateMobFile(Path path) throws IOException {
    HStoreFile storeFile = null;
    try {
      storeFile = new HStoreFile(region.getFilesystem(), path, conf, this.cacheConf,
          BloomType.NONE, isPrimaryReplicaStore());
      storeFile.initReader();
    } catch (IOException e) {
      LOG.error("Fail to open mob file[" + path + "], keep it in temp directory.", e);
      throw e;
    } finally {
      if (storeFile != null) {
        storeFile.closeStoreFile(false);
      }
    }
  }

};