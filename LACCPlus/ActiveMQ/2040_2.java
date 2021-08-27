//,temp,CamelRoutesBrokerPlugin.java,60,66,temp,RuntimeConfigurationPlugin.java,35,42
//,3
public class xxx {
    @Override
    public Broker installPlugin(Broker broker) throws Exception {
        LOG.info("installing runtimeConfiguration plugin");
        RuntimeConfigurationBroker runtimeConfigurationBroker = new RuntimeConfigurationBroker(broker);
        runtimeConfigurationBroker.setCheckPeriod(getCheckPeriod());

        return runtimeConfigurationBroker;
    }

};