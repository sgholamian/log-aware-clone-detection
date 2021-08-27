//,temp,ExceptionTest.java,112,137,temp,RomeksExceptionTest.java,69,93
//,3
public class xxx {
    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        final Processor exceptionThrower = new Processor() {
            public void process(Exchange exchange) throws Exception {
                LOG.debug("About to throw exception on " + exchange);

                exchange.getIn().setBody("<exception/>");
                throw new IllegalArgumentException("Exception thrown intentionally.");
            }
        };

        return new RouteBuilder() {
            public void configure() {
                errorHandler(deadLetterChannel("mock:error").redeliveryDelay(0));

                onException(IllegalArgumentException.class).to("mock:exception");

                from("direct:start").recipientList().simple("direct:${header.route}").to("mock:result");

                from("direct:a").setBody(constant("<some-value/>")).process(exceptionThrower).to("mock:result");

                from("direct:b").process(exceptionThrower).setBody(constant("<some-value/>")).to("mock:result");
            }
        };
    }

};