//,temp,PeerTransportTest.java,118,122,temp,IndividualDeadLetterViaXmlTest.java,43,47
//,3
public class xxx {
    protected Connection createConnection(int i) throws JMSException {
        LOG.info("creating connection ....");
        ActiveMQConnectionFactory fac = new ActiveMQConnectionFactory("peer://" + getClass().getName() + "/node" + i);
        return fac.createConnection();
    }

};