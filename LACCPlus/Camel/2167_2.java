//,temp,DisruptorRouteTest.java,71,106,temp,SedaRouteTest.java,88,121
//,3
public class xxx {
    @Test
    public void testThatShowsEndpointResolutionIsNotConsistent() throws Exception {
        final CountDownLatch latch = new CountDownLatch(1);

        CamelContext context = new DefaultCamelContext();

        // lets add some routes
        context.addRoutes(new RouteBuilder() {
            public void configure() {
                from("seda:test.a").to("seda:test.b");
                from("seda:test.b").process(new Processor() {
                    public void process(Exchange e) {
                        log.debug("Received exchange: " + e.getIn());
                        latch.countDown();
                    }
                });
            }
        });

        context.start();

        // now lets fire in a message
        Endpoint endpoint = context.getEndpoint("seda:test.a");
        Exchange exchange = endpoint.createExchange();
        exchange.getIn().setHeader("cheese", 123);

        Producer producer = endpoint.createProducer();
        producer.process(exchange);

        // now lets sleep for a while
        assertTrue(latch.await(5, TimeUnit.SECONDS));

        context.stop();
    }

};