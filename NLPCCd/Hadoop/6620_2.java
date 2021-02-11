//,temp,sample_7526.java,2,14,temp,sample_7525.java,2,11
//,3
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