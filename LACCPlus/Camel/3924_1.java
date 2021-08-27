//,temp,JmsToJmsTransactedSecurityTest.java,37,62,temp,LuceneIndexAndQueryProducerIT.java,94,116
//,3
public class xxx {
    @Test
    public void testJmsSecurityFailure() throws Exception {
        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("activemq:queue:foo")
                        .transacted()
                        .to("log:foo")
                        .to("activemq:queue:bar");

                from("activemq:queue:bar").to("mock:bar");
            }
        });
        context.start();

        MockEndpoint mock = getMockEndpoint("mock:bar");
        mock.expectedMessageCount(0);

        template.sendBody("activemq:queue:foo", "Hello World");
        // get the message that got rolled back
        Exchange exch = consumer.receive("activemq:queue:foo", 250);
        if (exch != null) {
            LOG.info("Cleaned up orphaned message: " + exch);
        }
        mock.assertIsSatisfied(3000);
    }

};