//,temp,HiveMetaStoreUtils.java,124,141,temp,HiveMetaStoreUtils.java,80,98
//,3
public class xxx {
  public static Deserializer getDeserializer(Configuration conf, org.apache.hadoop.hive.metastore.api.Table table,
      boolean skipConfError, String lib) throws MetaException {
    AbstractSerDe deserializer;
    try {
      deserializer = ReflectionUtil.newInstance(conf.getClassByName(lib).asSubclass(AbstractSerDe.class), conf);
    } catch (Exception e) {
      throw new MetaException(e.getClass().getName() + " " + e.getMessage());
    }

    try {
      deserializer.initialize(conf, MetaStoreUtils.getTableMetadata(table), null);
    } catch (SerDeException e) {
      if (!skipConfError) {
        LOG.error("error in initSerDe: " + e.getClass().getName() + " " + e.getMessage(), e);
        throw new MetaException(e.getClass().getName() + " " + e.getMessage());
      }
    }
    return deserializer;
  }

};