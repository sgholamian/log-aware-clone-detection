//,temp,StAXXPathSplitChoicePerformanceTest.java,140,149,temp,TokenPairIteratorSplitChoicePerformanceTest.java,102,111
//,3
public class xxx {
                            public void process(Exchange exchange) throws Exception {
                                String xml = exchange.getIn().getBody(String.class);
                                assertTrue(xml.contains("<amount>44</amount>"), xml);

                                int num = small.incrementAndGet();
                                if (num % 100 == 0) {
                                    log.info("Processed {} small messages: {}", num, xml);
                                    log.debug(xml);
                                }
                            }

};