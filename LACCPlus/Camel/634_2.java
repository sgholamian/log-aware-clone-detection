//,temp,DisruptorRouteTest.java,79,89,temp,JmsSimpleRequestCustomReplyToTest.java,123,138
//,3
public class xxx {
    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            public void configure() throws Exception {
                from(componentName + ":queue:hello").process(exchange -> {
                    assertEquals("Hello World", exchange.getIn().getBody());

                    myReplyTo = exchange.getIn().getHeader("MyReplyQeueue", String.class);
                    LOG.debug("ReplyTo: " + myReplyTo);

                    LOG.debug("Ahh I cannot send a reply. Someone else must do it.");
                    latch.countDown();
                }).to("mock:result");
            }
        };
    }

};