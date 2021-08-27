//,temp,ClusterServiceSelectors.java,62,80,temp,ServiceRegistrySelectors.java,61,79
//,2
public class xxx {
        @Override
        public Optional<CamelClusterService> select(Collection<CamelClusterService> services) {
            Optional<Map.Entry<Integer, List<CamelClusterService>>> highPriorityServices = services.stream()
                    .collect(Collectors.groupingBy(CamelClusterService::getOrder))
                    .entrySet().stream()
                    .min(Comparator.comparingInt(Map.Entry::getKey));

            if (highPriorityServices.isPresent()) {
                if (highPriorityServices.get().getValue().size() == 1) {
                    return Optional.of(highPriorityServices.get().getValue().iterator().next());
                } else {
                    LOGGER.warn("Multiple CamelClusterService instances available for highest priority (order={}, items={})",
                            highPriorityServices.get().getKey(),
                            highPriorityServices.get().getValue());
                }
            }

            return Optional.empty();
        }

};