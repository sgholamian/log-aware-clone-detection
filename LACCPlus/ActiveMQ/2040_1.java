//,temp,CamelRoutesBrokerPlugin.java,60,66,temp,RuntimeConfigurationPlugin.java,35,42
//,3
public class xxx {
    public Broker installPlugin(Broker broker) throws Exception {
        CamelRoutesBroker answer = new CamelRoutesBroker(broker);
        answer.setCheckPeriod(getCheckPeriod());
        answer.setRoutesFile(getRoutesFile());
        LOG.info("Installing CamelRoutesBroker");
        return answer;
    }

};