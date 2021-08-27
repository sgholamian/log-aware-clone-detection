//,temp,MinaConsumer.java,302,314,temp,MinaProducer.java,406,418
//,2
public class xxx {
    protected void configureDataGramCodecFactory(
            final String type, final IoService service, final MinaConfiguration configuration) {
        ProtocolCodecFactory codecFactory = configuration.getCodec();
        if (codecFactory == null) {
            codecFactory = new MinaUdpProtocolCodecFactory(this.getEndpoint().getCamelContext());

            if (LOG.isDebugEnabled()) {
                LOG.debug("{}: Using CodecFactory: {}", type, codecFactory);
            }
        }

        addCodecFactory(service, codecFactory);
    }

};