//,temp,sample_4275.java,2,11,temp,sample_4276.java,2,15
//,3
public class xxx {
public static TempletonStorage getStorageInstance(Configuration conf) {
TempletonStorage storage = null;
try {
storage = (TempletonStorage) JavaUtils.loadClass(conf.get(TempletonStorage.STORAGE_CLASS)) .newInstance();
} catch (Exception e) {
try {
storage = new HDFSStorage();
} catch (Exception ex) {


log.info("couldn t create storage");
}
}
}

};