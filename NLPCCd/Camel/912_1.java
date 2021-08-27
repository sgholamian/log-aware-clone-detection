//,temp,sample_3361.java,2,15,temp,sample_3360.java,2,13
//,3
public class xxx {
public void process(Exchange exchange) throws Exception {
Object body = exchange.getIn().getBody();
if (body != null) {
Address destinationAddress = exchange.getIn().getHeader(JGroupsEndpoint.HEADER_JGROUPS_DEST, Address.class);
Address sourceAddress = exchange.getIn().getHeader(JGroupsEndpoint.HEADER_JGROUPS_SRC, Address.class);
if (destinationAddress != null) {
}
if (sourceAddress != null) {


log.info("posting from custom source address");
}
}
}

};