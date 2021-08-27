//,temp,SelectorAwareVTThatDropsMessagesWhenNoConsumer.java,121,139,temp,AMQ4899Test.java,142,164
//,3
public class xxx {
    private void setupBroker(String uri) {
        try {
            broker = BrokerFactory.createBroker(uri);

            VirtualDestinationInterceptor interceptor = new VirtualDestinationInterceptor();
            VirtualTopic virtualTopic = new VirtualTopic();
            virtualTopic.setName("VirtualOrders.>");
            virtualTopic.setSelectorAware(true);
            VirtualDestination[] virtualDestinations = { virtualTopic };
            interceptor.setVirtualDestinations(virtualDestinations);
            broker.setDestinationInterceptors(new DestinationInterceptor[]{interceptor});

            SubQueueSelectorCacheBrokerPlugin subQueueSelectorCacheBrokerPlugin = new SubQueueSelectorCacheBrokerPlugin();
            BrokerPlugin[] updatedPlugins = {subQueueSelectorCacheBrokerPlugin};
            broker.setPlugins(updatedPlugins);

            broker.setUseJmx(false);
            broker.start();
            broker.waitUntilStarted();
        } catch (Exception e) {
            LOG.error("Failed creating broker", e);
        }
    }

};