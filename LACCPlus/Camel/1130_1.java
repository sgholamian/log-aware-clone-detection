//,temp,StAXXPathSplitChoicePerformanceTest.java,140,149,temp,TokenPairIteratorSplitChoicePerformanceTest.java,102,111
//,3
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