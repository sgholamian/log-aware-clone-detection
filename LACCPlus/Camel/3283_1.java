//,temp,DisruptorRouteTest.java,42,52,temp,TransactedTopicProducerTest.java,70,91
//,3
public class xxx {
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

};