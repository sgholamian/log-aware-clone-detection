//,temp,sample_6321.java,2,12,temp,sample_6322.java,2,13
//,3
public class xxx {
protected void doStart() throws Exception {
if (sessionId.get() == null) {
client = configuration.createConsulClient(getCamelContext());
sessionClient = client.sessionClient();
keyValueClient = client.keyValueClient();
sessionId.set( sessionClient.createSession( ImmutableSession.builder() .name(getNamespace()) .ttl(configuration.getSessionTtl() + "s") .lockDelay(configuration.getSessionLockDelay() + "s") .build() ).getId() );


log.info("acquired session with id");
}
}

};