//,temp,sample_6372.java,2,12,temp,sample_6371.java,2,11
//,3
public class xxx {
public void unmountDatastore(String uuid) throws Exception {
HostDatastoreSystemMO hostDatastoreSystemMo = getHostDatastoreSystemMO();
if (!hostDatastoreSystemMo.deleteDatastore(uuid)) {
String msg = "Unable to unmount datastore. uuid: " + uuid;
s_logger.error(msg);
throw new Exception(msg);
}


log.info("vcenter api trace unmountdatastore done");
}

};