//,temp,PullRequestConsumer.java,39,58,temp,PullRequestFilesProducer.java,38,50
//,3
public class xxx {
    public PullRequestConsumer(GitHubEndpoint endpoint, Processor processor) throws Exception {
        super(endpoint, processor);

        Registry registry = endpoint.getCamelContext().getRegistry();
        Object service = registry.lookupByName(GitHubConstants.GITHUB_PULL_REQUEST_SERVICE);
        if (service != null) {
            LOG.debug("Using PullRequestService found in registry {}", service.getClass().getCanonicalName());
            pullRequestService = (PullRequestService) service;
        } else {
            pullRequestService = new PullRequestService();
        }

        initService(pullRequestService);

        LOG.info("GitHub PullRequestConsumer: Indexing current pull requests...");
        List<PullRequest> pullRequests = pullRequestService.getPullRequests(getRepository(), "open");
        if (!pullRequests.isEmpty()) {
            lastOpenPullRequest = pullRequests.get(0).getNumber();
        }
    }

};