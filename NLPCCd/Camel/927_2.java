//,temp,sample_4249.java,2,15,temp,sample_3359.java,2,11
//,3
public class xxx {
public void process(Exchange exchange) throws Exception {
Object body = exchange.getIn().getBody();
if (body != null) {
Address destinationAddress = exchange.getIn().getHeader(JGroupsEndpoint.HEADER_JGROUPS_DEST, Address.class);
Address sourceAddress = exchange.getIn().getHeader(JGroupsEndpoint.HEADER_JGROUPS_SRC, Address.class);


log.info("posting to cluster");
}
}

};