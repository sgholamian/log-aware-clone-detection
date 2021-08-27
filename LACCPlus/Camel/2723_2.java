//,temp,MllpTcpServerConsumer.java,106,115,temp,MllpTcpServerConsumer.java,95,104
//,2
public class xxx {
    @ManagedOperation(description = "Close Connections")
    public void closeConnections() {

        for (TcpSocketConsumerRunnable consumerRunnable : consumerRunnables.keySet()) {
            if (consumerRunnable != null) {
                log.info("Close Connection called via JMX for address {}", consumerRunnable.getCombinedAddress());
                consumerRunnable.closeSocket();
            }
        }
    }

};