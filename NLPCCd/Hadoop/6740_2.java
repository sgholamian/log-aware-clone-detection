//,temp,sample_5470.java,2,9,temp,sample_6460.java,2,10
//,3
public class xxx {
private static void warnForDeprecatedConfigs(Configuration conf) {
for (String key : ImmutableList.of( "fs.checkpoint.size", "dfs.namenode.checkpoint.size")) {
if (conf.get(key) != null) {


log.info("configuration key is deprecated ignoring instead please specify a value for");
}
}
}

};