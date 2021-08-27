//,temp,CxfRsProducer.java,302,393,temp,CxfRsProducer.java,132,192
//,3
public class xxx {
    protected void invokeHttpClient(Exchange exchange) throws Exception {
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

        // handle cookies
        CookieHandler cookieHandler = ((CxfRsEndpoint) getEndpoint()).getCookieHandler();
        loadCookies(exchange, client, cookieHandler);

        // invoke the client
        Object response = null;
        if (responseClass == null || Response.class.equals(responseClass)) {
            response = client.invoke(httpMethod, body);
        } else {
            if (Collection.class.isAssignableFrom(responseClass)) {
                if (genericType instanceof ParameterizedType) {
                    // Get the collection member type first
                    Type[] actualTypeArguments = ((ParameterizedType) genericType).getActualTypeArguments();
                    response = client.invokeAndGetCollection(httpMethod, body, (Class<?>) actualTypeArguments[0]);

                } else {
                    throw new CamelExchangeException(
                            "Header " + CxfConstants.CAMEL_CXF_RS_RESPONSE_GENERIC_TYPE + " not found in message", exchange);
                }
            } else {
                response = client.invoke(httpMethod, body, responseClass);
            }
        }
        int statesCode = client.getResponse().getStatus();
        // handle cookies
        saveCookies(exchange, client, cookieHandler);
        //Throw exception on a response > 207
        //http://en.wikipedia.org/wiki/List_of_HTTP_status_codes
        if (throwException) {
            if (response instanceof Response) {
                int respCode = ((Response) response).getStatus();
                if (respCode > 207) {
                    throw populateCxfRsProducerException(exchange, (Response) response, respCode);
                }
            }
        }
        // set response
        if (exchange.getPattern().isOutCapable()) {
            LOG.trace("Response body = {}", response);
            exchange.getOut().getHeaders().putAll(exchange.getIn().getHeaders());
            exchange.getOut().setBody(binding.bindResponseToCamelBody(response, exchange));
            exchange.getOut().getHeaders().putAll(binding.bindResponseHeadersToCamelHeaders(response, exchange));
            exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, statesCode);
        } else {
            // just close the input stream of the response object
            if (response instanceof Response) {
                ((Response) response).close();
            }
        }
    }

};