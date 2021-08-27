//,temp,ClusterServiceSelectors.java,40,49,temp,ServiceRegistrySelectors.java,39,48
//,2
public class xxx {
        @Override
        public Optional<ServiceRegistry> select(Collection<ServiceRegistry> services) {
            if (services != null && services.size() == 1) {
                return Optional.of(services.iterator().next());
            } else {
                LOGGER.warn("Multiple ServiceRegistry instances available (items={})", services);
            }

            return Optional.empty();
        }

};