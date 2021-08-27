//,temp,sample_5066.java,2,14,temp,sample_5065.java,2,13
//,3
public class xxx {
public void onJobExecute(final JobExecutionContext jobExecutionContext) throws JobExecutionException {
boolean run = true;
LoadBalancer balancer = getLoadBalancer();
if (balancer instanceof ServiceSupport) {
run = ((ServiceSupport) balancer).isRunAllowed();
}
if (!run) {


log.info("cannot execute quartz job with context because processor is not started");
}
}

};