//,temp,DurableConsumerCloseAndReconnectTcpTest.java,176,188,temp,DurableConsumerCloseAndReconnectTcpTest.java,85,95
//,3
public class xxx {
                    @Override
                    public synchronized void close() throws IOException {
                        LOG.info("delaying close");
                        try {
                            TimeUnit.MILLISECONDS.sleep(500);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        super.close();
                    }

};