//,temp,DisruptorRouteTest.java,79,89,temp,JmsSimpleRequestCustomReplyToTest.java,123,138
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