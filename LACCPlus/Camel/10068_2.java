//,temp,StAXXPathSplitChoicePerformanceTest.java,88,153,temp,TokenPairIteratorSplitChoicePerformanceTest.java,79,138
//,3
public class xxx {
    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from(fileUri("?initialDelay=0&delay=10&noop=true")).process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        log.info("Starting to process file");
                        watch.restart();
                    }
                }).split().tokenizeXML("order").streaming().choice().when().xpath("/order/amount < 10")
                        .process(new Processor() {
                            public void process(Exchange exchange) throws Exception {
                                String xml = exchange.getIn().getBody(String.class);
                                assertTrue(xml.contains("<amount>3</amount>"), xml);

                                int num = tiny.incrementAndGet();
                                if (num % 100 == 0) {
                                    log.info("Processed " + num + " tiny messages");
                                    log.debug(xml);
                                }
                            }
                        }).when().xpath("/order/amount < 50").process(new Processor() {
                            public void process(Exchange exchange) throws Exception {
                                String xml = exchange.getIn().getBody(String.class);
                                assertTrue(xml.contains("<amount>44</amount>"), xml);

                                int num = small.incrementAndGet();
                                if (num % 100 == 0) {
                                    log.info("Processed {} small messages: {}", num, xml);
                                    log.debug(xml);
                                }
                            }
                        }).when().xpath("/order/amount < 100").process(new Processor() {
                            public void process(Exchange exchange) throws Exception {
                                String xml = exchange.getIn().getBody(String.class);
                                assertTrue(xml.contains("<amount>88</amount>"), xml);

                                int num = med.incrementAndGet();
                                if (num % 100 == 0) {
                                    log.info("Processed " + num + " medium messages");
                                    log.debug(xml);
                                }
                            }
                        }).otherwise().process(new Processor() {
                            public void process(Exchange exchange) throws Exception {
                                String xml = exchange.getIn().getBody(String.class);
                                assertTrue(xml.contains("<amount>123</amount>"), xml);

                                int num = large.incrementAndGet();
                                if (num % 100 == 0) {
                                    log.info("Processed " + num + " large messages");
                                    log.debug(xml);
                                }
                            }
                        }).end() // choice
                        .end(); // split
            }
        };
    }

};