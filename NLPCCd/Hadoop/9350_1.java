//,temp,sample_5303.java,2,11,temp,sample_5316.java,2,12
//,3
public class xxx {
private BlockReader getBlockReaderLocal() throws InvalidToken {
if (pathInfo == null) {
pathInfo = clientContext.getDomainSocketFactory() .getPathInfo(inetSocketAddress, conf.getShortCircuitConf());
}
if (!pathInfo.getPathState().getUsableForShortCircuit()) {


log.info("is not usable for short circuit giving up on blockreaderlocal");
}
}

};