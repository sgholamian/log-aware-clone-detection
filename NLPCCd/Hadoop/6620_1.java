//,temp,sample_7526.java,2,14,temp,sample_7525.java,2,11
//,3
public class xxx {
private void initializePipeline(String user) {
RequestInterceptorChainWrapper chainWrapper = null;
synchronized (this.userPipelineMap) {
if (this.userPipelineMap.containsKey(user)) {
return;
}
chainWrapper = new RequestInterceptorChainWrapper();
this.userPipelineMap.put(user, chainWrapper);
}


log.info("initializing request processing pipeline for the user");
}

};