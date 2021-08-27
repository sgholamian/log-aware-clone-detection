//,temp,MllpTcpServerConsumer.java,106,115,temp,MllpTcpServerConsumer.java,95,104
//,2
public class xxx {
    @ManagedOperation(description = "Reset Connections")
    public void resetConnections() {

        for (TcpSocketConsumerRunnable consumerRunnable : consumerRunnables.keySet()) {
            if (consumerRunnable != null) {
                log.info("Reset Connection called via JMX for address {}", consumerRunnable.getCombinedAddress());
                consumerRunnable.resetSocket();
            }
        }
    }

};