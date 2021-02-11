//,temp,sample_861.java,2,16,temp,sample_860.java,2,16
//,2
public class xxx {
public void dummy_method(){
fs.mkdirs(cf1Dir);
fs.mkdirs(cf2Dir);
String spA = getStoragePolicyName(fs, cf1Dir);
String spB = getStoragePolicyName(fs, cf2Dir);
assertEquals("HOT", spA);
assertEquals("HOT", spB);
HFileOutputFormat2.configureStoragePolicy(conf, fs, HFileOutputFormat2.combineTableNameSuffix(TABLE_NAMES[0].getName(), FAMILIES[0]), cf1Dir);
HFileOutputFormat2.configureStoragePolicy(conf, fs, HFileOutputFormat2.combineTableNameSuffix(TABLE_NAMES[0].getName(), FAMILIES[1]), cf2Dir);
spA = getStoragePolicyName(fs, cf1Dir);
spB = getStoragePolicyName(fs, cf2Dir);


log.info("storage policy of cf");
}

};