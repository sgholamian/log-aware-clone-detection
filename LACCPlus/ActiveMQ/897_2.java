//,temp,PublishOnTopicConsumedMessageTest.java,37,50,temp,FailoverTxSlowAckTest.java,102,120
//,3
public class xxx {
                    @Override
                    public void send(ProducerBrokerExchange producerExchange, org.apache.activemq.command.Message messageSend) throws Exception {
                        super.send(producerExchange, messageSend);
                        sendCount++;
                        if (sendCount > 1) {
                            // need new thread b/c we have the service write lock
                            executorService.execute(new Runnable() {
                                @Override
                                public void run() {
                                    LOG.info("Stopping broker before commit...");
                                    try {
                                        broker.stop();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }}});

};