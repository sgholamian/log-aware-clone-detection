//,temp,sample_476.java,2,16,temp,sample_3187.java,2,17
//,3
public class xxx {
public void dummy_method(){
String serializationLib = getSerializationLib();
try {
if (hasMetastoreBasedSchema(SessionState.getSessionConf(), serializationLib)) {
return tTable.getSd().getCols();
} else if (forMs && !shouldStoreFieldsInMetastore( SessionState.getSessionConf(), serializationLib, tTable.getParameters())) {
return Hive.getFieldsFromDeserializerForMsStorage(this, getDeserializer());
} else {
return HiveMetaStoreUtils.getFieldsFromDeserializer(getTableName(), getDeserializer());
}
} catch (Exception e) {


log.info("unable to get field from serde");
}
}

};