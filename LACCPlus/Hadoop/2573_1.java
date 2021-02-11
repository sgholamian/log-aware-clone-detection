//,temp,S3AInstrumentation.java,538,545,temp,S3AInstrumentation.java,522,529
//,3
public class xxx {
  public void decrementGauge(Statistic op, long count) {
    MutableGaugeLong gauge = lookupGauge(op.getSymbol());
    if (gauge != null) {
      gauge.decr(count);
    } else {
      LOG.debug("No Gauge: {}", op);
    }
  }

};