//,temp,PullRequestStateProducer.java,42,57,temp,CommitConsumer.java,45,59
//,3
public class xxx {
    public PullRequestStateProducer(GitHubEndpoint endpoint) throws Exception {
        super(endpoint);

        Registry registry = endpoint.getCamelContext().getRegistry();
        Object service = registry.lookupByName(GitHubConstants.GITHUB_COMMIT_SERVICE);
        if (service != null) {
            LOG.debug("Using CommitService found in registry {}", service.getClass().getCanonicalName());
            commitService = (CommitService) service;
        } else {
            commitService = new CommitService();
        }
        initService(commitService);

        state = endpoint.getState();
        targetUrl = endpoint.getTargetUrl();
    }

};