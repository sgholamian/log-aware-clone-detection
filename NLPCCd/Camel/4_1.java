//,temp,sample_267.java,2,11,temp,sample_3287.java,2,15
//,3
public class xxx {
public PullRequestStateProducer(GitHubEndpoint endpoint) throws Exception {
super(endpoint);
Registry registry = endpoint.getCamelContext().getRegistry();
Object service = registry.lookupByName(GitHubConstants.GITHUB_COMMIT_SERVICE);
if (service != null) {


log.info("using commitservice found in registry");
}
}

};