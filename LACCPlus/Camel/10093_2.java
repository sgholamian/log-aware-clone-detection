//,temp,Olingo4Producer.java,47,115,temp,Olingo2Producer.java,49,116
//,3
public class xxx {
    @Override
    public boolean process(final Exchange exchange, final AsyncCallback callback) {
        // properties for method arguments
        final Map<String, Object> properties = new HashMap<>();
        properties.putAll(endpoint.getEndpointProperties());
        propertiesHelper.getExchangeProperties(exchange, properties);

        // let the endpoint and the Producer intercept properties
        endpoint.interceptProperties(properties);
        interceptProperties(properties);

        // create response handler
        properties.put(Olingo2Endpoint.RESPONSE_HANDLER_PROPERTY, new Olingo2ResponseHandler<Object>() {
            @Override
            public void onResponse(Object response, Map<String, String> responseHeaders) {
                if (resultIndex != null) {
                    response = resultIndex.filterResponse(response);
                }

                // producer returns a single response, even for methods with
                // List return types
                exchange.getOut().setBody(response);
                // copy headers
                exchange.getOut().setHeaders(exchange.getIn().getHeaders());

                // Add http response headers
                exchange.getOut().setHeader(Olingo2Constants.PROPERTY_PREFIX + RESPONSE_HTTP_HEADERS, responseHeaders);

                interceptResult(response, exchange);

                callback.done(false);
            }

            @Override
            public void onException(Exception ex) {
                exchange.setException(ex);
                callback.done(false);
            }

            @Override
            public void onCanceled() {
                exchange.setException(new RuntimeCamelException("OData HTTP Request cancelled!"));
                callback.done(false);
            }
        });

        // decide which method to invoke
        final ApiMethod method = findMethod(exchange, properties);
        if (method == null) {
            // synchronous failure
            callback.done(true);
            return true;
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Invoking operation {} with {}", method.getName(), properties.keySet());
        }

        try {
            doInvokeMethod(method, properties);
        } catch (Exception e) {
            exchange.setException(RuntimeCamelException.wrapRuntimeCamelException(e));
            callback.done(true);
            return true;
        }

        return false;
    }

};