//,temp,ReplicationSourceFactory.java,35,53,temp,CleanerChore.java,270,284
//,3
public class xxx {
  private T newFileCleaner(String className, Configuration conf) {
    try {
      Class<? extends FileCleanerDelegate> c = Class.forName(className).asSubclass(
        FileCleanerDelegate.class);
      @SuppressWarnings("unchecked")
      T cleaner = (T) c.getDeclaredConstructor().newInstance();
      cleaner.setConf(conf);
      cleaner.init(this.params);
      return cleaner;
    } catch (Exception e) {
      LOG.warn("Can NOT create CleanerDelegate={}", className, e);
      // skipping if can't instantiate
      return null;
    }
  }

};