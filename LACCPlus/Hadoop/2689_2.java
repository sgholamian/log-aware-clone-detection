//,temp,Job.java,1724,1734,temp,AliyunOSSUtils.java,46,56
//,3
public class xxx {
  public static int intPositiveOption(
      Configuration conf, String key, int defVal) {
    int v = conf.getInt(key, defVal);
    if (v <= 0) {
      LOG.warn(key + " is configured to " + v
          + ", will use default value: " + defVal);
      v = defVal;
    }

    return v;
  }

};