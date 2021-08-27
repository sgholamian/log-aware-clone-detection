//,temp,StAXXPathSplitChoicePerformanceTest.java,114,123,temp,TokenPairIteratorSplitChoicePerformanceTest.java,124,133
//,2
public class xxx {
                            public void process(Exchange exchange) throws Exception {
                                String xml = exchange.getIn().getBody(String.class);
                                assertTrue(xml.contains("<amount>44</amount>"), xml);

                                int num = small.incrementAndGet();
                                if (num % 100 == 0) {
                                    log.info("Processed " + num + " small messages");
                                    log.debug(xml);
                                }
                            }

};