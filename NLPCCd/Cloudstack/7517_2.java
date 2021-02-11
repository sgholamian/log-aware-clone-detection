//,temp,sample_5799.java,2,11,temp,sample_2219.java,2,11
//,2
public class xxx {
public StorageVol getVolume(StoragePool pool, String volName) {
StorageVol vol = null;
try {
vol = pool.storageVolLookupByName(volName);
} catch (LibvirtException e) {


log.info("could not find volume");
}
}

};