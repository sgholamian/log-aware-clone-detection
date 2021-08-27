//,temp,CxfRsProducer.java,787,814,temp,CxfRsProducer.java,688,721
//,3
public class xxx {
        @Override
        public void completed(Response response) {
            try {
                if (shouldHandleError(response)) {
                    handleError(response);
                    return;
                }
                // handle cookies
                saveCookies(exchange, client, cxfRsEndpoint.getCookieHandler());
                if (!exchange.getPattern().isOutCapable()) {
                    return;
                }

                LOG.trace("Response body = {}", response);
                exchange.getOut().getHeaders().putAll(exchange.getIn().getHeaders());
                final CxfRsBinding binding = cxfRsEndpoint.getBinding();
                exchange.getOut().getHeaders().putAll(binding.bindResponseHeadersToCamelHeaders(response, exchange));

                if (genericType != null && !genericType.equals(Void.TYPE)) {
                    GenericType genericType = new GenericType(this.genericType);
                    exchange.getOut().setBody(binding.bindResponseToCamelBody(response.readEntity(genericType), exchange));
                } else if (responseClass != null && !responseClass.equals(Void.TYPE)) {
                    exchange.getOut().setBody(binding.bindResponseToCamelBody(response.readEntity(responseClass), exchange));
                } else {
                    exchange.getOut().setBody(binding.bindResponseToCamelBody(response, exchange));
                }
                exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, response.getStatus());
            } catch (Exception exception) {
                LOG.error("Error while processing response", exception);
                fail(exception);
            } finally {
                callback.done(false);
            }
        }

};