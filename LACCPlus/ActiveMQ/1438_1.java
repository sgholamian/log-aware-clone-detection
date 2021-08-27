//,temp,AMQ4221Test.java,147,160,temp,QueueView.java,59,65
//,3
public class xxx {
                @Override
                public void run() {
                    while (!done.get()) {
                        try {
                            Thread.sleep(DLQ_PURGE_INTERVAL);
                            LOG.info("Purge DLQ, current size: " + dlq.getDestinationStatistics().getMessages().getCount());
                            dlq.purge();
                        } catch (InterruptedException allDone) {
                        } catch (Throwable e) {
                            e.printStackTrace();
                            exceptions.add(e);
                        }
                    }
                }

};