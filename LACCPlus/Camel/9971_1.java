//,temp,ExceptionTest.java,112,137,temp,RomeksExceptionTest.java,69,93
//,3
public class xxx {
    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        final Processor exceptionThrower = new Processor() {
            public void process(Exchange exchange) throws Exception {
                exchange.getIn().setBody("<exception/>");
                throw new IllegalArgumentException("Exception thrown intentionally.");
            }
        };

        return new RouteBuilder() {
            public void configure() {
                errorHandler(deadLetterChannel("mock:error").redeliveryDelay(0).maximumRedeliveries(3));

                if (getName().endsWith("WithLongHandler")) {
                    log.debug("Using long exception handler");
                    onException(IllegalArgumentException.class).setBody(constant("<not-handled/>")).to("mock:exception");
                } else if (getName().endsWith("WithHandler")) {
                    log.debug("Using exception handler");
                    onException(IllegalArgumentException.class).to("mock:exception");
                }
                from("direct:start").process(exceptionThrower).to("mock:result");
                from("direct:start2").to("direct:intermediate").to("mock:result");
                from("direct:intermediate").setBody(constant("<some-value/>")).process(exceptionThrower).to("mock:result");
            }
        };
    }

};