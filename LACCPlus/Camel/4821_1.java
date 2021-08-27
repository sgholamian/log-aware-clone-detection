//,temp,DisruptorRouteTest.java,34,69,temp,SedaRouteTest.java,53,86
//,3
public class xxx {
    @Test
    void testDisruptorQueue() throws Exception {
        final CountDownLatch latch = new CountDownLatch(1);

        final CamelContext context = new DefaultCamelContext();

        // lets add some routes
        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() {
                from("disruptor:test.a").to("disruptor:test.b");
                from("disruptor:test.b").process(new Processor() {
                    @Override
                    public void process(final Exchange e) {
                        log.debug("Received exchange: " + e.getIn());
                        latch.countDown();
                    }
                });
            }
        });

        context.start();

        // now lets fire in a message
        final Endpoint endpoint = context.getEndpoint("disruptor:test.a");
        final Exchange exchange = endpoint.createExchange();
        exchange.getIn().setHeader("cheese", 123);

        final Producer producer = endpoint.createProducer();
        producer.process(exchange);

        // now lets sleep for a while
        assertTrue(latch.await(5, TimeUnit.SECONDS));

        context.stop();
    }

};