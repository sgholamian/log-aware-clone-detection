//,temp,sample_2065.java,2,19,temp,sample_2064.java,2,20
//,3
public class xxx {
public void dummy_method(){
result = seeIfConfAtThisLocation("METASTORE_HOME", name, true);
if (result == null) {
result = seeIfConfAtThisLocation("HIVE_CONF_DIR", name, false);
if (result == null) {
result = seeIfConfAtThisLocation("HIVE_HOME", name, true);
if (result == null) {
URI jarUri = null;
try {
jarUri = MetastoreConf.class.getProtectionDomain().getCodeSource().getLocation().toURI();
} catch (Throwable e) {


log.info("cannot get jar uri");
}
}
}
}
}

};