//,temp,sample_5052.java,2,12,temp,sample_2788.java,2,12
//,2
public class xxx {
protected void configureDataGramCodecFactory(final String type, final IoService service, final Mina2Configuration configuration) {
ProtocolCodecFactory codecFactory = configuration.getCodec();
if (codecFactory == null) {
codecFactory = new Mina2UdpProtocolCodecFactory(this.getEndpoint().getCamelContext());
if (LOG.isDebugEnabled()) {


log.info("using codecfactory");
}
}
}

};