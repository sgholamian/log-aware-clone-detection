//,temp,GrpcProducer.java,155,194,temp,GrpcConsumer.java,92,148
//,3
public class xxx {
    protected void initializeServer() throws Exception {
        NettyServerBuilder serverBuilder;
        BindableService bindableService = getBindableServiceFactory().createBindableService(this);
        ServerInterceptor headerInterceptor = new GrpcHeaderInterceptor();

        if (!ObjectHelper.isEmpty(configuration.getHost()) && !ObjectHelper.isEmpty(configuration.getPort())) {
            LOG.debug("Building gRPC server on {}:{}", configuration.getHost(), configuration.getPort());
            serverBuilder
                    = NettyServerBuilder.forAddress(new InetSocketAddress(configuration.getHost(), configuration.getPort()));
        } else {
            throw new IllegalArgumentException("No server start properties (host, port) specified");
        }

        if (configuration.isRouteControlledStreamObserver()
                && configuration.getConsumerStrategy() == GrpcConsumerStrategy.AGGREGATION) {
            throw new IllegalArgumentException(
                    "Consumer strategy AGGREGATION and routeControlledStreamObserver are not compatible. Set the consumer strategy to PROPAGATION");
        }

        if (configuration.getNegotiationType() == NegotiationType.TLS) {
            ObjectHelper.notNull(configuration.getKeyCertChainResource(), "keyCertChainResource");
            ObjectHelper.notNull(configuration.getKeyResource(), "keyResource");

            SslContextBuilder sslContextBuilder
                    = SslContextBuilder
                            .forServer(
                                    ResourceHelper.resolveResourceAsInputStream(endpoint.getCamelContext(),
                                            configuration.getKeyCertChainResource()),
                                    ResourceHelper.resolveResourceAsInputStream(endpoint.getCamelContext(),
                                            configuration.getKeyResource()),
                                    configuration.getKeyPassword())
                            .clientAuth(ClientAuth.REQUIRE)
                            .sslProvider(SslProvider.OPENSSL);

            if (ObjectHelper.isNotEmpty(configuration.getTrustCertCollectionResource())) {
                sslContextBuilder
                        = sslContextBuilder.trustManager(ResourceHelper.resolveResourceAsInputStream(endpoint.getCamelContext(),
                                configuration.getTrustCertCollectionResource()));
            }

            serverBuilder = serverBuilder.sslContext(GrpcSslContexts.configure(sslContextBuilder).build());
        }

        if (configuration.getAuthenticationType() == GrpcAuthType.JWT) {
            ObjectHelper.notNull(configuration.getJwtSecret(), "jwtSecret");

            serverBuilder = serverBuilder.intercept(new JwtServerInterceptor(
                    configuration.getJwtAlgorithm(), configuration.getJwtSecret(),
                    configuration.getJwtIssuer(), configuration.getJwtSubject()));
        }

        server = serverBuilder.addService(ServerInterceptors.intercept(bindableService, headerInterceptor))
                .maxInboundMessageSize(configuration.getMaxMessageSize())
                .flowControlWindow(configuration.getFlowControlWindow())
                .maxConcurrentCallsPerConnection(configuration.getMaxConcurrentCallsPerConnection())
                .build();
    }

};