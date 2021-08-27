//,temp,HiveMetaStoreUtils.java,124,141,temp,HiveMetaStoreUtils.java,80,98
//,3
public class xxx {
  static public Deserializer getDeserializer(Configuration conf,
      org.apache.hadoop.hive.metastore.api.Partition part,
      org.apache.hadoop.hive.metastore.api.Table table) throws MetaException {
    String lib = part.getSd().getSerdeInfo().getSerializationLib();
    try {
      AbstractSerDe deserializer = ReflectionUtil.newInstance(conf.getClassByName(lib).
        asSubclass(AbstractSerDe.class), conf);
      deserializer.initialize(conf, MetaStoreUtils.getTableMetadata(table),
          MetaStoreUtils.getPartitionMetadata(part, table));
      return deserializer;
    } catch (RuntimeException e) {
      throw e;
    } catch (Throwable e) {
      LOG.error("error in initSerDe: " + e.getClass().getName() + " "
          + e.getMessage(), e);
      throw new MetaException(e.getClass().getName() + " " + e.getMessage());
    }
  }

};