//,temp,GrpcProducer.java,155,194,temp,GrpcConsumer.java,92,148
//,3
public class xxx {
    protected void initializeChannel() throws Exception {
        NettyChannelBuilder channelBuilder;

        if (!ObjectHelper.isEmpty(configuration.getHost()) && !ObjectHelper.isEmpty(configuration.getPort())) {
            LOG.info("Creating channel to the remote gRPC server {}:{}", configuration.getHost(), configuration.getPort());
            channelBuilder = NettyChannelBuilder.forAddress(configuration.getHost(), configuration.getPort());
        } else {
            throw new IllegalArgumentException("No connection properties (host or port) specified");
        }
        if (configuration.getNegotiationType() == NegotiationType.TLS) {
            ObjectHelper.notNull(configuration.getKeyCertChainResource(), "keyCertChainResource");
            ObjectHelper.notNull(configuration.getKeyResource(), "keyResource");

            ClassResolver classResolver = endpoint.getCamelContext().getClassResolver();

            SslContextBuilder sslContextBuilder = GrpcSslContexts.forClient()
                    .sslProvider(SslProvider.OPENSSL)
                    .keyManager(
                            ResourceHelper.resolveResourceAsInputStream(endpoint.getCamelContext(),
                                    configuration.getKeyCertChainResource()),
                            ResourceHelper.resolveResourceAsInputStream(endpoint.getCamelContext(),
                                    configuration.getKeyResource()),
                            configuration.getKeyPassword());

            if (ObjectHelper.isNotEmpty(configuration.getTrustCertCollectionResource())) {
                sslContextBuilder
                        = sslContextBuilder.trustManager(ResourceHelper.resolveResourceAsInputStream(endpoint.getCamelContext(),
                                configuration.getTrustCertCollectionResource()));
            }

            channelBuilder = channelBuilder.sslContext(sslContextBuilder.build());
        }

        channel = channelBuilder.negotiationType(configuration.getNegotiationType())
                .flowControlWindow(configuration.getFlowControlWindow())
                .userAgent(configuration.getUserAgent())
                .maxInboundMessageSize(configuration.getMaxMessageSize())
                .intercept(configuration.getClientInterceptors())
                .build();
    }

};