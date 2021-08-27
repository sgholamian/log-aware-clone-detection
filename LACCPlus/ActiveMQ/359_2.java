//,temp,MBeanBridgeDestination.java,83,104,temp,MBeanBridgeDestination.java,60,80
//,3
public class xxx {
    public void onOutboundMessage(Message message) {
        ActiveMQDestination destination = message.getDestination();
        NetworkDestinationContainer networkDestinationContainer;

        if ((networkDestinationContainer = outboundDestinationViewMap.get(destination)) == null) {
            ObjectName bridgeObjectName = bridge.getMbeanObjectName();
            try {
                ObjectName objectName = BrokerMBeanSupport.createNetworkOutBoundDestinationObjectName(bridgeObjectName, destination);
                NetworkDestinationView networkDestinationView = new NetworkDestinationView(networkBridgeView, destination.getPhysicalName());
                AnnotatedMBean.registerMBean(brokerService.getManagementContext(), networkDestinationView, objectName);

                networkDestinationContainer = new NetworkDestinationContainer(networkDestinationView, objectName);
                outboundDestinationViewMap.put(destination, networkDestinationContainer);
                networkDestinationView.messageSent();
            } catch (Exception e) {
                LOG.warn("Failed to register " + destination, e);
            }
        } else {
            networkDestinationContainer.view.messageSent();
        }
    }

};