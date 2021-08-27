//,temp,sample_267.java,2,11,temp,sample_3287.java,2,15
//,3
public class xxx {
public PullRequestConsumer(GitHubEndpoint endpoint, Processor processor) throws Exception {
super(endpoint, processor);
Registry registry = endpoint.getCamelContext().getRegistry();
Object service = registry.lookupByName(GitHubConstants.GITHUB_PULL_REQUEST_SERVICE);
if (service != null) {
pullRequestService = (PullRequestService) service;
} else {
pullRequestService = new PullRequestService();
}
initService(pullRequestService);


log.info("github pullrequestconsumer indexing current pull requests");
}

};