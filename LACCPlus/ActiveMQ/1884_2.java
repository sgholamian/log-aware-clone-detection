//,temp,DestinationsTest.java,64,69,temp,JavaDestinationsTest.java,101,106
//,2
public class xxx {
    protected void printDestinations() throws Exception {
        ActiveMQDestination[] destinations = brokerService.getRegionBroker().getDestinations();
        for (ActiveMQDestination destination : destinations) {
            LOG.info("Broker destination: " + destination.toString());
        }
    }

};