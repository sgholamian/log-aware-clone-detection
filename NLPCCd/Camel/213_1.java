//,temp,sample_6110.java,2,12,temp,sample_4396.java,2,11
//,3
public class xxx {
public AbstractGitHubConsumer(GitHubEndpoint endpoint, Processor processor) throws Exception {
super(endpoint, processor);
this.endpoint = endpoint;
Registry registry = endpoint.getCamelContext().getRegistry();
Object service = registry.lookupByName(GitHubConstants.GITHUB_REPOSITORY_SERVICE);
if (service != null) {


log.info("using repositoryservice found in registry");
}
}

};