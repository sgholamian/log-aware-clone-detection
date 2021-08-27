//,temp,PayLoadDataFormatFeature.java,86,104,temp,PayLoadDataFormatFeature.java,61,84
//,3
public class xxx {
    @Override
    public void initialize(Client client, Bus bus) {
        client.getEndpoint().put("org.apache.cxf.binding.soap.addNamespaceContext", "true");
        removeFaultInInterceptorFromClient(client);

        // Need to remove some interceptors that are incompatible
        // We don't support JAX-WS Holders for PAYLOAD (not needed anyway)
        // and thus we need to remove those interceptors to prevent Holder
        // object from being created and stuck into the contents list
        // instead of Source objects
        removeInterceptor(client.getEndpoint().getInInterceptors(),
                HolderInInterceptor.class);
        removeInterceptor(client.getEndpoint().getOutInterceptors(),
                HolderOutInterceptor.class);
        // The SoapHeaderInterceptor maps various headers onto method parameters.
        // At this point, we expect all the headers to remain as headers, not
        // part of the body, so we remove that one.
        removeInterceptor(client.getEndpoint().getBinding().getInInterceptors(),
                SoapHeaderInterceptor.class);
        client.getEndpoint().getBinding().getInInterceptors().add(new ConfigureDocLitWrapperInterceptor(true));
        resetPartTypes(client.getEndpoint().getBinding());

        LOG.info("Initialized CXF Client: {} in Payload mode with allow streaming: {}", client, allowStreaming);
    }

};