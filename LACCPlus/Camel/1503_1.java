//,temp,PullRequestCommentProducer.java,44,64,temp,AbstractGitHubConsumer.java,39,54
//,3
public class xxx {
    public PullRequestCommentProducer(GitHubEndpoint endpoint) throws Exception {
        super(endpoint);

        Registry registry = endpoint.getCamelContext().getRegistry();
        Object service = registry.lookupByName(GitHubConstants.GITHUB_PULL_REQUEST_SERVICE);
        if (service != null) {
            LOG.debug("Using PullRequestService found in registry {}", service.getClass().getCanonicalName());
            pullRequestService = (PullRequestService) service;
        } else {
            pullRequestService = new PullRequestService();
        }
        initService(pullRequestService);

        service = registry.lookupByName("githbIssueService");
        if (service != null) {
            issueService = (IssueService) service;
        } else {
            issueService = new IssueService();
        }
        initService(issueService);
    }

};