//,temp,TokenPairIteratorSplitChoicePerformanceTest.java,113,122,temp,XPathSplitChoicePerformanceTest.java,137,146
//,2
public class xxx {
                            public void process(Exchange exchange) throws Exception {
                                String xml = exchange.getIn().getBody(String.class);
                                assertTrue(xml.contains("<amount>123</amount>"), xml);

                                int num = large.incrementAndGet();
                                if (num % 100 == 0) {
                                    log.info("Processed " + num + " large messages");
                                    log.debug(xml);
                                }
                            }

};