//,temp,sample_6210.java,3,11,temp,sample_6211.java,3,13
//,3
public class xxx {
private T1 lookupConnection(final String fullUri, final Map<String, Object> parameters) throws Exception {
if (fullUri == null || fullUri.isEmpty()) {
throw new IllegalArgumentException("Invalid URI: " + fullUri);
}
final ConnectionId id = parseConnectionId(fullUri, parameters);
synchronized (this) {


log.info("locating connection");
}
}

};