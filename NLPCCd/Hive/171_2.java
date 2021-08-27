//,temp,sample_4281.java,2,18,temp,sample_4481.java,2,18
//,2
public class xxx {
public void dummy_method(){
if (getStorageHandler() != null) {
tbl.setProperty( org.apache.hadoop.hive.metastore.api.hive_metastoreConstants.META_TABLE_STORAGE, getStorageHandler());
}
HiveStorageHandler storageHandler = tbl.getStorageHandler();
String serDeClassName;
if (getSerde() == null) {
if (storageHandler == null) {
serDeClassName = PlanUtils.getDefaultSerDe().getName();
} else {
serDeClassName = storageHandler.getSerDeClass().getName();


log.info("use storagehandler supplied for materialized view");
}
}
}

};