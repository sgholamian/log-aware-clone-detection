//,temp,CxfRsProducer.java,302,393,temp,CxfRsProducer.java,132,192
//,3
public class xxx {
    protected void invokeAsyncHttpClient(Exchange exchange, final AsyncCallback callback) throws Exception {
        Message inMessage = exchange.getIn();
        JAXRSClientFactoryBean cfb = clientFactoryBeanCache.get(CxfEndpointUtils
                .getEffectiveAddress(exchange, ((CxfRsEndpoint) getEndpoint()).getAddress()));
        Bus bus = ((CxfRsEndpoint) getEndpoint()).getBus();
        // We need to apply the bus setting from the CxfRsEndpoint which is not use the default bus
        if (bus != null) {
            cfb.setBus(bus);
        }
        WebClient client = cfb.createWebClient();
        ((CxfRsEndpoint) getEndpoint()).getChainedCxfRsEndpointConfigurer().configureClient(client);
        String httpMethod = inMessage.getHeader(Exchange.HTTP_METHOD, String.class);
        Class<?> responseClass = inMessage.getHeader(CxfConstants.CAMEL_CXF_RS_RESPONSE_CLASS, Class.class);
        Type genericType = inMessage.getHeader(CxfConstants.CAMEL_CXF_RS_RESPONSE_GENERIC_TYPE, Type.class);
        Object[] pathValues = inMessage.getHeader(CxfConstants.CAMEL_CXF_RS_VAR_VALUES, Object[].class);
        String path = inMessage.getHeader(Exchange.HTTP_PATH, String.class);

        if (LOG.isTraceEnabled()) {
            LOG.trace("HTTP method = {}", httpMethod);
            LOG.trace("path = {}", path);
            LOG.trace("responseClass = {}", responseClass);
        }

        // set the path
        if (path != null) {
            if (ObjectHelper.isNotEmpty(pathValues) && pathValues.length > 0) {
                client.path(path, pathValues);
            } else {
                client.path(path);
            }
        }

        CxfRsEndpoint cxfRsEndpoint = (CxfRsEndpoint) getEndpoint();
        CxfRsBinding binding = cxfRsEndpoint.getBinding();
        Object body = getBody(exchange, inMessage, httpMethod, cxfRsEndpoint, binding);
        setupClientMatrix(client, exchange);
        setupClientQueryAndHeaders(client, exchange);

        // ensure the CONTENT_TYPE header can be retrieved
        if (ObjectHelper.isEmpty(inMessage.getHeader(Exchange.CONTENT_TYPE, String.class))
                && ObjectHelper.isNotEmpty(client.getHeaders().get(Exchange.CONTENT_TYPE))) {
            inMessage.setHeader(Exchange.CONTENT_TYPE, client.getHeaders().get(Exchange.CONTENT_TYPE).get(0));
        }

        //Build message entity
        Entity<Object> entity = binding.bindCamelMessageToRequestEntity(body, inMessage, exchange, client);

        // handle cookies
        CookieHandler cookieHandler = ((CxfRsEndpoint) getEndpoint()).getCookieHandler();
        loadCookies(exchange, client, cookieHandler);

        // invoke the client
        if (responseClass == null || Response.class.equals(responseClass)) {
            client.async().method(httpMethod, entity,
                    new CxfInvocationCallback(client, exchange, cxfRsEndpoint, null, callback, null));
        } else {
            client.async().method(httpMethod, entity,
                    new CxfInvocationCallback(client, exchange, cxfRsEndpoint, responseClass, callback, genericType));
        }

    }

};