//,temp,sample_3726.java,2,8,temp,sample_5389.java,2,11
//,3
public class xxx {
private static HttpClient makeHttpClient(Lifecycle lifecycle) {
final int numConnection = HiveConf .getIntVar(SessionState.getSessionConf(), HiveConf.ConfVars.HIVE_DRUID_NUM_HTTP_CONNECTION );
final Period readTimeout = new Period( HiveConf.getVar(SessionState.getSessionConf(), HiveConf.ConfVars.HIVE_DRUID_HTTP_READ_TIMEOUT ));


log.info("creating druid http client with max parallel connections and ms read timeout");
}

};