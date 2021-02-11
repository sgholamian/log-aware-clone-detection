//,temp,sample_3625.java,2,10,temp,sample_379.java,2,9
//,2
public class xxx {
public void init(Configuration conf) {
isEnabled = conf.getBoolean(KEY_AUTH_SERVICE_CACHING_ENABLE, KEY_AUTH_SERVICE_CACHING_ENABLE_DEFAULT);
if (isEnabled) {


log.info("initializing cachingauthorizer instance");
}
}

};