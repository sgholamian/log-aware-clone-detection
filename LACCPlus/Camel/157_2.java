//,temp,XPathSplitChoicePerformanceTest.java,124,133,temp,XPathSplitChoicePerformanceTest.java,98,107
//,2
public class xxx {
                            public void process(Exchange exchange) throws Exception {
                                String xml = exchange.getIn().getBody(String.class);
                                assertTrue(xml.contains("<amount>3</amount>"), xml);

                                int num = tiny.incrementAndGet();
                                if (num % 100 == 0) {
                                    log.info("Processed " + num + " tiny messages");
                                    log.debug(xml);
                                }
                            }

};