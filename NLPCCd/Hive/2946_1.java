//,temp,sample_4448.java,2,13,temp,sample_4449.java,2,15
//,3
public class xxx {
public static void initSharedCacheAsync(Configuration conf) {
String clazzName = null;
boolean isEnabled = false;
try {
clazzName = MetastoreConf.getVar(conf, MetastoreConf.ConfVars.RAW_STORE_IMPL);
isEnabled = JavaUtils.getClass(clazzName, RawStore.class).isAssignableFrom(CachedStore.class);
} catch (MetaException e) {


log.info("cannot instantiate metastore class");
}
}

};