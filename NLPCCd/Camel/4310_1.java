//,temp,sample_3567.java,2,16,temp,sample_3569.java,2,18
//,3
public class xxx {
private ZooKeeperEndpoint createCandidateNode(CamelContext camelContext) {
ZooKeeperEndpoint zep = camelContext.getEndpoint(uri, ZooKeeperEndpoint.class);
zep.getConfiguration().setCreate(true);
String fullpath = createFullPathToCandidate(zep);
Exchange e = zep.createExchange();
e.setPattern(ExchangePattern.InOut);
e.getIn().setHeader(ZooKeeperMessage.ZOOKEEPER_NODE, fullpath);
e.getIn().setHeader(ZooKeeperMessage.ZOOKEEPER_CREATE_MODE, CreateMode.EPHEMERAL_SEQUENTIAL);
producerTemplate.send(zep, e);
if (e.isFailed()) {


log.info("error setting up election node");
}
}

};