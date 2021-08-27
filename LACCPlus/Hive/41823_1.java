//,temp,HMSHandler.java,6285,6299,temp,TxnUtils.java,120,130
//,3
public class xxx {
  private StorageSchemaReader getStorageSchemaReader() throws MetaException {
    if (storageSchemaReader == null) {
      String className =
          MetastoreConf.getVar(conf, MetastoreConf.ConfVars.STORAGE_SCHEMA_READER_IMPL);
      Class<? extends StorageSchemaReader> readerClass =
          JavaUtils.getClass(className, StorageSchemaReader.class);
      try {
        storageSchemaReader = readerClass.newInstance();
      } catch (InstantiationException|IllegalAccessException e) {
        LOG.error("Unable to instantiate class " + className, e);
        throw new MetaException(e.getMessage());
      }
    }
    return storageSchemaReader;
  }

};