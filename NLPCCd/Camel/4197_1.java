//,temp,sample_3846.java,2,17,temp,sample_3845.java,2,11
//,3
public class xxx {
public boolean storeFile(String name, Exchange exchange, long size) throws GenericFileOperationFailedException {
ObjectHelper.notNull(session, "session");
ScpConfiguration cfg = endpoint.getConfiguration();
int timeout = cfg.getConnectTimeout();
if (LOG.isTraceEnabled()) {
}
String file = getRemoteFile(name, cfg);
InputStream is = null;
if (exchange.getIn().getBody() == null) {
if (endpoint.isAllowNullBody()) {


log.info("writing empty file");
}
}
}

};