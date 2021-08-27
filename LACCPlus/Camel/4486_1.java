//,temp,CxfRsProducer.java,787,814,temp,CxfRsProducer.java,688,721
//,3
public class xxx {
        @Override
        public void completed(Object body) {
            try {
                Response response = client.getResponse();
                // handle cookies
                saveCookies(exchange, client, cxfRsEndpoint.getCookieHandler());
                //handle error
                if (shouldHandleError(response)) {
                    handleError(response);
                    return;
                }
                if (!exchange.getPattern().isOutCapable()) {
                    return;
                }

                LOG.trace("Response body = {}", response);
                exchange.getOut().getHeaders().putAll(exchange.getIn().getHeaders());
                final CxfRsBinding binding = cxfRsEndpoint.getBinding();
                exchange.getOut().getHeaders().putAll(binding.bindResponseHeadersToCamelHeaders(response, exchange));
                exchange.getOut().setBody(binding.bindResponseToCamelBody(body, exchange));
                exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, response.getStatus());
            } catch (Exception exception) {
                LOG.error("Error while processing response", exception);
                fail(exception);
            } finally {
                callback.done(false);
            }
        }

};