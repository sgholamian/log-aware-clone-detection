//,temp,LoggingBrokerPlugin.java,435,441,temp,LoggingBrokerPlugin.java,393,399
//,3
public class xxx {
    @Override
    public void addBroker(Connection connection, BrokerInfo info) {
        if (isLogAll() || isLogInternalEvents()) {
            LOG.info("Adding Broker {}", info.getBrokerName());
        }
        super.addBroker(connection, info);
    }

};