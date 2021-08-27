//,temp,JmsProducerClient.java,350,359,temp,JmsConsumerClient.java,279,288
//,2
public class xxx {
    protected void sleep() {
        if (client.getRecvDelay() > 0) {
            try {
                LOG.trace("Sleeping for " + client.getRecvDelay() + " milliseconds");
                Thread.sleep(client.getRecvDelay());
            } catch (java.lang.InterruptedException ex) {
                LOG.warn(ex.getMessage());
            }
        }
    }

};