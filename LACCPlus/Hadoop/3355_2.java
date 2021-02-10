//,temp,S3AUtils.java,857,867,temp,S3AUtils.java,839,846
//,2
public class xxx {
  static int intOption(Configuration conf, String key, int defVal, int min) {
    int v = conf.getInt(key, defVal);
    Preconditions.checkArgument(v >= min,
        String.format("Value of %s: %d is below the minimum value %d",
            key, v, min));
    LOG.debug("Value of {} is {}", key, v);
    return v;
  }

};