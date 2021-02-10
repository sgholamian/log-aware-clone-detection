//,temp,RouterRMAdminService.java,244,271,temp,RouterClientRMService.java,508,536
//,3
public class xxx {
  private RequestInterceptorChainWrapper initializePipeline(String user) {
    synchronized (this.userPipelineMap) {
      if (this.userPipelineMap.containsKey(user)) {
        LOG.info("Request to start an already existing user: {}"
            + " was received, so ignoring.", user);
        return userPipelineMap.get(user);
      }

      RequestInterceptorChainWrapper chainWrapper =
          new RequestInterceptorChainWrapper();
      try {
        // We should init the pipeline instance after it is created and then
        // add to the map, to ensure thread safe.
        LOG.info("Initializing request processing pipeline for application "
            + "for the user: {}", user);

        ClientRequestInterceptor interceptorChain =
            this.createRequestInterceptorChain();
        interceptorChain.init(user);
        chainWrapper.init(interceptorChain);
      } catch (Exception e) {
        LOG.error("Init ClientRequestInterceptor error for user: " + user, e);
        throw e;
      }

      this.userPipelineMap.put(user, chainWrapper);
      return chainWrapper;
    }
  }

};