//,temp,PeerTransportTest.java,118,122,temp,IndividualDeadLetterViaXmlTest.java,43,47
//,3
public class xxx {
    protected Destination createDlqDestination() {
        String queueName = "Test.DLQ." + getClass().getName() + "." + getName();
        LOG.info("Using queue name: " + queueName);
        return new ActiveMQQueue(queueName);
    }

};