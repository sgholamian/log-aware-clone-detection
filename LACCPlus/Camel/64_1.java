//,temp,ClusterServiceSelectors.java,40,49,temp,ServiceRegistrySelectors.java,39,48
//,2
public class xxx {
        @Override
        public Optional<CamelClusterService> select(Collection<CamelClusterService> services) {
            if (services != null && services.size() == 1) {
                return Optional.of(services.iterator().next());
            } else {
                LOGGER.warn("Multiple CamelClusterService instances available (items={})", services);
            }

            return Optional.empty();
        }

};