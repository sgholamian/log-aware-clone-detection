//,temp,LoggingBrokerPlugin.java,451,467,temp,LoggingBrokerPlugin.java,214,229
//,3
public class xxx {
    @Override
    public Set<ActiveMQDestination> getDurableDestinations() {
        Set<ActiveMQDestination> result = super.getDurableDestinations();
        if (isLogAll() || isLogInternalEvents()) {
            if (result == null) {
                LOG.info("Get Durable Destinations returned empty list.");
            } else {
                StringBuffer destinations = new StringBuffer();
                for (ActiveMQDestination dest : result) {
                    destinations.append(destinations.length() > 0 ? ", " : "");
                    destinations.append(dest.getPhysicalName());
                }
                LOG.info("Get Durable Destinations: {}", destinations);
            }
        }
        return result;
    }

};