//,temp,sample_154.java,2,10,temp,sample_3308.java,2,12
//,3
public class xxx {
protected ContainerManagementProtocol newProxy(final YarnRPC rpc, String containerManagerBindAddr, ContainerId containerId, Token token) throws InvalidToken {
if (token == null) {
throw new InvalidToken("No NMToken sent for " + containerManagerBindAddr);
}
final InetSocketAddress cmAddr = NetUtils.createSocketAddr(containerManagerBindAddr);
if (LOG.isDebugEnabled()) {


log.info("opening proxy");
}
}

};