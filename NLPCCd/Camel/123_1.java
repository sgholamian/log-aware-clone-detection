//,temp,sample_3286.java,2,11,temp,sample_2365.java,2,14
//,3
public class xxx {
public PullRequestConsumer(GitHubEndpoint endpoint, Processor processor) throws Exception {
super(endpoint, processor);
Registry registry = endpoint.getCamelContext().getRegistry();
Object service = registry.lookupByName(GitHubConstants.GITHUB_PULL_REQUEST_SERVICE);
if (service != null) {


log.info("using pullrequestservice found in registry");
}
}

};