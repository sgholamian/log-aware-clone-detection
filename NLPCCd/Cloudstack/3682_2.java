//,temp,sample_5451.java,2,16,temp,sample_5450.java,2,15
//,2
public class xxx {
private void deleteElastistorVolume(StoragePool pool, boolean managed) {
String poolid = pool.getUuid();
boolean status;
try {
status = ElastistorUtil.deleteElastistorTsm(poolid, managed);
} catch (Throwable e) {
throw new CloudRuntimeException("Failed to delete primary storage on elastistor" + e);
}
if (status == true) {


log.info("deletion of elastistor primary storage complete");
}
}

};