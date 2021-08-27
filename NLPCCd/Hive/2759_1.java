//,temp,sample_476.java,2,16,temp,sample_3187.java,2,17
//,3
public class xxx {
private List<FieldSchema> getColsInternal(boolean forMs) {
try {
String serializationLib = tPartition.getSd().getSerdeInfo().getSerializationLib();
if (Table.hasMetastoreBasedSchema(SessionState.getSessionConf(), serializationLib)) {
return tPartition.getSd().getCols();
} else if (forMs && !Table.shouldStoreFieldsInMetastore( SessionState.getSessionConf(), serializationLib, table.getParameters())) {
return Hive.getFieldsFromDeserializerForMsStorage(table, getDeserializer());
}
return HiveMetaStoreUtils.getFieldsFromDeserializer(table.getTableName(), getDeserializer());
} catch (Exception e) {


log.info("unable to get cols from serde");
}
}

};