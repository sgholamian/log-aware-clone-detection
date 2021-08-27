//,temp,LoggingBrokerPlugin.java,477,483,temp,LoggingBrokerPlugin.java,401,407
//,3
public class xxx {
    @Override
    public void removeBroker(Connection connection, BrokerInfo info) {
        if (isLogAll() || isLogInternalEvents()) {
            LOG.info("Removing Broker {}", info.getBrokerName());
        }
        super.removeBroker(connection, info);
    }

};