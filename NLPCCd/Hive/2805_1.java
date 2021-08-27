//,temp,sample_1773.java,2,13,temp,sample_1774.java,2,15
//,3
public class xxx {
private ApplicationId determineLlapId(final HiveConf conf, QueryPlan plan) throws IOException {
for (TezTask tezTask : Utilities.getTezTasks(plan.getRootTasks())) {
if (!tezTask.getWork().getLlapMode()) continue;
String hosts = HiveConf.getVar(conf, HiveConf.ConfVars.LLAP_DAEMON_SERVICE_HOSTS);
if (hosts != null && !hosts.isEmpty()) {
ApplicationId llapId = LlapRegistryService.getClient(conf).getApplicationId();


log.info("the query will use llap instance");
}
}
}

};