//,temp,AdvisoryConsumer.java,54,64,temp,ActiveMQMessageConsumer.java,748,754
//,3
public class xxx {
    public synchronized void dispose() {
        if (!closed) {
            try {
                this.connection.asyncSendPacket(info.createRemoveCommand());
            } catch (JMSException e) {
                LOG.debug("Failed to send remove command: " + e, e);
            }
            this.connection.removeDispatcher(info.getConsumerId());
            closed = true;
        }
    }

};