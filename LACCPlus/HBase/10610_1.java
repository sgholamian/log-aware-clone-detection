//,temp,MobUtils.java,763,777,temp,HMobStore.java,283,297
//,3
public class xxx {
  private static void validateMobFile(Configuration conf, FileSystem fs, Path path,
      CacheConfig cacheConfig, boolean primaryReplica) throws IOException {
    HStoreFile storeFile = null;
    try {
      storeFile = new HStoreFile(fs, path, conf, cacheConfig, BloomType.NONE, primaryReplica);
      storeFile.initReader();
    } catch (IOException e) {
      LOG.error("Failed to open mob file[" + path + "], keep it in temp directory.", e);
      throw e;
    } finally {
      if (storeFile != null) {
        storeFile.closeStoreFile(false);
      }
    }
  }

};