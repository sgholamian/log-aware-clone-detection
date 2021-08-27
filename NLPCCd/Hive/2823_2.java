//,temp,sample_5299.java,2,17,temp,sample_5300.java,2,17
//,3
public class xxx {
public void dummy_method(){
final String fragmentId = LlapTezUtils.getFragmentId(job);
final String dagId = LlapTezUtils.getDagId(job);
final String queryId = HiveConf.getVar(job, HiveConf.ConfVars.HIVEQUERYID);
MDC.put("dagId", dagId);
MDC.put("queryId", queryId);
TezCounters taskCounters = null;
if (fragmentId != null) {
MDC.put("fragmentId", fragmentId);
taskCounters = FragmentCountersMap.getCountersForFragment(fragmentId);
} else {


log.info("not using tez counters as fragment id string is null");
}
}

};