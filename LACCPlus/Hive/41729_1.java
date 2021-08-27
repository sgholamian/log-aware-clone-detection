//,temp,HiveStreamingConnection.java,847,852,temp,HiveStreamingConnection.java,840,845
//,3
public class xxx {
  private static void setHiveConf(HiveConf conf, HiveConf.ConfVars var, boolean value) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("Overriding HiveConf setting : " + var + " = " + value);
    }
    conf.setBoolVar(var, value);
  }

};