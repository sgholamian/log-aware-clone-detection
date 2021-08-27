//,temp,KubernetesReplicationControllersConsumerTest.java,120,125,temp,SplitterParallelIssueTest.java,61,67
//,3
public class xxx {
                            @Override
                            public void process(Exchange exchange) throws Exception {
                                int num = exchange.getIn().getBody(int.class);
                                final long sleep = num * delay;
                                log.info("Sleep for " + sleep + "ms");
                                Thread.sleep(sleep);
                            }

};