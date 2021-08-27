//,temp,PullRequestStateProducer.java,42,57,temp,CommitConsumer.java,45,59
//,3
public class xxx {
    public CommitConsumer(GitHubEndpoint endpoint, Processor processor, String branchName,
                          String startingSha) throws Exception {
        super(endpoint, processor);
        this.branchName = branchName;
        this.startingSha = startingSha;

        Registry registry = endpoint.getCamelContext().getRegistry();
        Object service = registry.lookupByName(GitHubConstants.GITHUB_COMMIT_SERVICE);
        if (service != null) {
            LOG.debug("Using CommitService found in registry {}", service.getClass().getCanonicalName());
            commitService = (CommitService) service;
        } else {
            commitService = new CommitService();
        }
    }

};