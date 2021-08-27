//,temp,sample_3567.java,2,16,temp,sample_3569.java,2,18
//,3
public class xxx {
public void dummy_method(){
Exchange e = zep.createExchange();
e.setPattern(ExchangePattern.InOut);
e.getIn().setHeader(ZooKeeperMessage.ZOOKEEPER_NODE, fullpath);
e.getIn().setHeader(ZooKeeperMessage.ZOOKEEPER_CREATE_MODE, CreateMode.EPHEMERAL_SEQUENTIAL);
producerTemplate.send(zep, e);
if (e.isFailed()) {
} else {
try {
camelContext.addRoutes(new ElectoralMonitorRoute(zep));
} catch (Exception ex) {


log.info("error configuring zookeeperelection");
}
}
}

};