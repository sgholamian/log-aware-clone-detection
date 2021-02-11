//,temp,sample_2811.java,2,9,temp,sample_2814.java,2,9
//,2
public class xxx {
public String createOvaForVolume(VolumeObjectTO volume) {
DataStoreTO storeTO = volume.getDataStore();
if (!(storeTO instanceof NfsTO)) {


log.info("can only handle nfs storage when create ova from volume");
}
}

};