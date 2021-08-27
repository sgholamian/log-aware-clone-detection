//,temp,sample_2065.java,2,19,temp,sample_2064.java,2,20
//,3
public class xxx {
public void dummy_method(){
if (result == null) {
result = seeIfConfAtThisLocation("HIVE_HOME", name, true);
if (result == null) {
URI jarUri = null;
try {
jarUri = MetastoreConf.class.getProtectionDomain().getCodeSource().getLocation().toURI();
} catch (Throwable e) {
}
result = seeIfConfAtThisLocation(new File(jarUri).getParent(), name, true);
if (result == null) {


log.info("unable to find config file");
}
}
}
}

};