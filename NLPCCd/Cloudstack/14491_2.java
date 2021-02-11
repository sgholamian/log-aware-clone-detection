//,temp,sample_2221.java,2,17,temp,sample_5800.java,2,17
//,2
public class xxx {
public StorageVol getVolume(StoragePool pool, String volName) {
StorageVol vol = null;
try {
vol = pool.storageVolLookupByName(volName);
} catch (LibvirtException e) {
}
if (vol == null) {
try {
refreshPool(pool);
} catch (LibvirtException e) {


log.info("failed to refresh pool");
}
}
}

};