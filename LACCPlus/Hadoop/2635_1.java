//,temp,LightWeightGSet.java,87,94,temp,RetryCacheMetrics.java,42,48
//,3
public class xxx {
  public LightWeightGSet(final int recommended_length) {
    final int actual = actualArrayLength(recommended_length);
    if (LOG.isDebugEnabled()) {
      LOG.debug("recommended=" + recommended_length + ", actual=" + actual);
    }
    entries = new LinkedElement[actual];
    hash_mask = entries.length - 1;
  }

};