//,temp,FailoverConsumerUnconsumedTest.java,246,263,temp,FailoverXATransactionTest.java,102,121
//,3
public class xxx {
                    @Override
                    public Subscription addConsumer(ConnectionContext context,
                            final ConsumerInfo info) throws Exception {
                         if (++consumerCount == maxConsumers + (watchTopicAdvisories ? 1:0)) {
                             context.setDontSendReponse(true);
                             Executors.newSingleThreadExecutor().execute(new Runnable() {
                                 public void run() {
                                     LOG.info("Stopping broker on consumer: " + info.getConsumerId());
                                     try {
                                         broker.stop();
                                     } catch (Exception e) {
                                         e.printStackTrace();
                                     }
                                 }
                             });
                         }
                        return super.addConsumer(context, info);
                    }

};