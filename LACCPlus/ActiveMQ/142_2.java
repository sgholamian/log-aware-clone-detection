//,temp,AdvisoryConsumer.java,54,64,temp,ActiveMQMessageConsumer.java,748,754
//,3
public class xxx {
    void doClose() throws JMSException {
        dispose();
        RemoveInfo removeCommand = info.createRemoveCommand();
        LOG.debug("remove: {}, lastDeliveredSequenceId: {}", getConsumerId(), lastDeliveredSequenceId);
        removeCommand.setLastDeliveredSequenceId(lastDeliveredSequenceId);
        this.session.asyncSendPacket(removeCommand);
    }

};