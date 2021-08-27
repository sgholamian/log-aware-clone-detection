//,temp,sample_3079.java,2,11,temp,sample_461.java,2,11
//,3
public class xxx {
public PullRequestCommentConsumer(GitHubEndpoint endpoint, Processor processor) throws Exception {
super(endpoint, processor);
Registry registry = endpoint.getCamelContext().getRegistry();
Object service = registry.lookupByName(GitHubConstants.GITHUB_PULL_REQUEST_SERVICE);
if (service != null) {


log.info("using pullrequestservice found in registry");
}
}

};