//,temp,sample_4397.java,2,15,temp,sample_1820.java,2,11
//,3
public class xxx {
public CommitConsumer(GitHubEndpoint endpoint, Processor processor, String branchName) throws Exception {
super(endpoint, processor);
Registry registry = endpoint.getCamelContext().getRegistry();
Object service = registry.lookupByName(GitHubConstants.GITHUB_COMMIT_SERVICE);
if (service != null) {
commitService = (CommitService) service;
} else {
commitService = new CommitService();
}
initService(commitService);


log.info("github commitconsumer indexing current commits");
}

};