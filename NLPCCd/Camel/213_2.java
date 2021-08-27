//,temp,sample_6110.java,2,12,temp,sample_4396.java,2,11
//,3
public class xxx {
public CommitConsumer(GitHubEndpoint endpoint, Processor processor, String branchName) throws Exception {
super(endpoint, processor);
Registry registry = endpoint.getCamelContext().getRegistry();
Object service = registry.lookupByName(GitHubConstants.GITHUB_COMMIT_SERVICE);
if (service != null) {


log.info("using commitservice found in registry");
}
}

};