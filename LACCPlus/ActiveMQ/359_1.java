//,temp,MBeanBridgeDestination.java,83,104,temp,MBeanBridgeDestination.java,60,80
//,3
public class xxx {
    public void onInboundMessage(Message message) {
        ActiveMQDestination destination = message.getDestination();
        NetworkDestinationContainer networkDestinationContainer;

        if ((networkDestinationContainer = inboundDestinationViewMap.get(destination)) == null) {
            ObjectName bridgeObjectName = bridge.getMbeanObjectName();
            try {
                ObjectName objectName = BrokerMBeanSupport.createNetworkInBoundDestinationObjectName(bridgeObjectName, destination);
                NetworkDestinationView networkDestinationView = new NetworkDestinationView(networkBridgeView, destination.getPhysicalName());
                AnnotatedMBean.registerMBean(brokerService.getManagementContext(), networkDestinationView, objectName);

                networkBridgeView.addNetworkDestinationView(networkDestinationView);
                networkDestinationContainer = new NetworkDestinationContainer(networkDestinationView, objectName);
                inboundDestinationViewMap.put(destination, networkDestinationContainer);
                networkDestinationView.messageSent();
            } catch (Exception e) {
                LOG.warn("Failed to register " + destination, e);
            }
        } else {
            networkDestinationContainer.view.messageSent();
        }
    }

};