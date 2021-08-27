//,temp,PayLoadDataFormatFeature.java,86,104,temp,PayLoadDataFormatFeature.java,61,84
//,3
public class xxx {
    @Override
    public void initialize(Server server, Bus bus) {
        server.getEndpoint().put("org.apache.cxf.binding.soap.addNamespaceContext", "true");
        server.getEndpoint().getBinding().getInInterceptors().add(new ConfigureDocLitWrapperInterceptor(true));
        if (server.getEndpoint().getBinding() instanceof SoapBinding) {
            server.getEndpoint().getBinding().getOutInterceptors().add(new SetSoapVersionInterceptor());
        }
        // Need to remove some interceptors that are incompatible
        // See above.
        removeInterceptor(server.getEndpoint().getInInterceptors(),
                HolderInInterceptor.class);
        removeInterceptor(server.getEndpoint().getOutInterceptors(),
                HolderOutInterceptor.class);
        removeInterceptor(server.getEndpoint().getBinding().getInInterceptors(),
                SoapHeaderInterceptor.class);
        resetPartTypes(server.getEndpoint().getBinding());

        LOG.info("Initialized CXF Server: {} in Payload mode with allow streaming: {}", server, allowStreaming);
    }

};