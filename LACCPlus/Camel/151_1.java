//,temp,AsyncEndpointRedeliveryErrorHandlerNonBlockedDelayTest.java,64,77,temp,AsyncEndpointRedeliveryErrorHandlerNonBlockedDelay2Test.java,67,80
//,2
public class xxx {
                    public void process(Exchange exchange) throws Exception {
                        LOG.info("Processing at attempt {} {}", attempt, exchange);

                        String body = exchange.getIn().getBody(String.class);
                        if (body.contains("World")) {
                            if (++attempt <= 2) {
                                LOG.info("Processing failed will thrown an exception");
                                throw new IllegalArgumentException("Damn");
                            }
                        }

                        exchange.getIn().setBody("Hello " + body);
                        LOG.info("Processing at attempt {} complete {}", attempt, exchange);
                    }

};