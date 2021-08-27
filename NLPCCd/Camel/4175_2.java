//,temp,sample_6213.java,3,17,temp,sample_6212.java,3,14
//,3
public class xxx {
private T1 lookupConnection(final String fullUri, final Map<String, Object> parameters) throws Exception {
if (fullUri == null || fullUri.isEmpty()) {
throw new IllegalArgumentException("Invalid URI: " + fullUri);
}
final ConnectionId id = parseConnectionId(fullUri, parameters);
synchronized (this) {
T1 connection = this.connections.get(id);


log.info("result");
}
}

};