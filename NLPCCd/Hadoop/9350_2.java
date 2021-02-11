//,temp,sample_5303.java,2,11,temp,sample_5316.java,2,12
//,3
public class xxx {
private BlockReader getRemoteBlockReaderFromDomain() throws IOException {
if (pathInfo == null) {
pathInfo = clientContext.getDomainSocketFactory() .getPathInfo(inetSocketAddress, conf.getShortCircuitConf());
}
if (!pathInfo.getPathState().getUsableForDataTransfer()) {
return null;
}


log.info("trying to create a remote block reader from the unix domain socket at");
}

};