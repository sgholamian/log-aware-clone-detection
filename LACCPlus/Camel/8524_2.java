//,temp,ZooKeeperServiceDiscovery.java,57,84,temp,ZooKeeperServiceRegistry.java,259,284
//,3
public class xxx {
    @Override
    protected void doStart() throws Exception {
        if (curator == null) {
            // Validation
            ObjectHelper.notNull(getCamelContext(), "Camel Context");
            ObjectHelper.notNull(configuration.getBasePath(), "ZooKeeper base path");

            LOGGER.debug("Starting ZooKeeper Curator with namespace '{}', nodes: '{}'",
                    configuration.getNamespace(),
                    String.join(",", configuration.getNodes()));

            curator = ZooKeeperCuratorHelper.createCurator(configuration);
            curator.start();
        }

        if (serviceDiscovery == null) {
            // Validation
            ObjectHelper.notNull(configuration.getBasePath(), "ZooKeeper base path");

            LOGGER.debug("Starting ZooKeeper ServiceDiscoveryBuilder with base path '{}'",
                    configuration.getBasePath());

            serviceDiscovery = ZooKeeperCuratorHelper.createServiceDiscovery(configuration, curator, MetaData.class);
            serviceDiscovery.start();
        }
    }

};