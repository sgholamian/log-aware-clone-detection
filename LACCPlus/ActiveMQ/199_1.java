//,temp,FailoverTransactionTest.java,508,529,temp,FailoverConsumerOutstandingCommitTest.java,206,227
//,3
public class xxx {
                    @Override
                    public void send(ProducerBrokerExchange producerExchange,
                                     org.apache.activemq.command.Message messageSend)
                            throws Exception {
                        // so send will hang as if reply is lost
                        super.send(producerExchange, messageSend);
                        if (firstSend) {
                            firstSend = false;

                            producerExchange.getConnectionContext().setDontSendReponse(true);
                            Executors.newSingleThreadExecutor().execute(new Runnable() {
                                public void run() {
                                    LOG.info("Stopping connection post send...");
                                    try {
                                        proxy.close();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }

};