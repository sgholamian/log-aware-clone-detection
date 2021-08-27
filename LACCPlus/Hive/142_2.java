//,temp,HMSHandler.java,10496,10511,temp,HMSHandler.java,10385,10399
//,3
public class xxx {
  @Override
  public List<RuntimeStat> get_runtime_stats(GetRuntimeStatsRequest rqst) throws TException {
    startFunction("get_runtime_stats");
    Exception ex = null;
    try {
      List<RuntimeStat> res = getMS().getRuntimeStats(rqst.getMaxWeight(), rqst.getMaxCreateTime());
      return res;
    } catch (MetaException e) {
      LOG.error("Caught exception", e);
      ex = e;
      throw e;
    } finally {
      endFunction("get_runtime_stats", ex == null, ex);
    }
  }

};