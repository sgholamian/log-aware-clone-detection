//,temp,PullRequestCommentProducer.java,44,64,temp,AbstractGitHubConsumer.java,39,54
//,3
public class xxx {
    public AbstractGitHubConsumer(GitHubEndpoint endpoint, Processor processor) throws Exception {
        super(endpoint, processor);
        this.endpoint = endpoint;

        Registry registry = endpoint.getCamelContext().getRegistry();
        Object service = registry.lookupByName(GitHubConstants.GITHUB_REPOSITORY_SERVICE);
        if (service != null) {
            LOG.debug("Using RepositoryService found in registry {}", service.getClass().getCanonicalName());
            repositoryService = (RepositoryService) service;
        } else {
            repositoryService = new RepositoryService();
        }

        initService(repositoryService);
        repository = repositoryService.getRepository(endpoint.getRepoOwner(), endpoint.getRepoName());
    }

};