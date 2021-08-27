//,temp,sample_4397.java,2,15,temp,sample_1820.java,2,11
//,3
public class xxx {
public GetCommitFileProducer(GitHubEndpoint endpoint) throws Exception {
super(endpoint);
Registry registry = endpoint.getCamelContext().getRegistry();
Object service = registry.lookupByName(GitHubConstants.GITHUB_DATA_SERVICE);
if (service != null) {


log.info("using dataservice found in registry");
}
}

};