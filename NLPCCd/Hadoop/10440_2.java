//,temp,sample_8740.java,2,11,temp,sample_7525.java,2,11
//,2
public class xxx {
private void initializePipeline(String user) {
RequestInterceptorChainWrapper chainWrapper = null;
synchronized (this.userPipelineMap) {
if (this.userPipelineMap.containsKey(user)) {


log.info("request to start an already existing user was received so ignoring");
}
}
}

};