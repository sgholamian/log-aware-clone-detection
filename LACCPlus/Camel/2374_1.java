//,temp,FacebookProducer.java,137,165,temp,AbstractApiProducer.java,135,164
//,3
public class xxx {
    private FacebookMethodsType findMethod(Exchange exchange, Map<String, Object> properties) {

        FacebookMethodsType method = null;
        final List<FacebookMethodsType> candidates = endpoint.getCandidates();
        if (processInBody(exchange, properties)) {

            // filter candidates based on endpoint and exchange properties
            final Set<String> argNames = properties.keySet();
            final List<FacebookMethodsType> filteredMethods = filterMethods(candidates, MatchType.SUPER_SET,
                    argNames.toArray(new String[argNames.size()]));

            // get the method to call
            if (filteredMethods.isEmpty()) {
                final Set<String> missing = getMissingProperties(endpoint.getMethod(),
                        endpoint.getNameStyle(), argNames);
                throw new RuntimeCamelException(
                        String.format("Missing properties for %s, need one or more from %s",
                                endpoint.getMethod(), missing));
            } else if (filteredMethods.size() == 1) {
                // found an exact match
                method = filteredMethods.get(0);
            } else {
                method = FacebookMethodsTypeHelper.getHighestPriorityMethod(filteredMethods);
                LOG.warn("Calling highest priority method {} from methods {}", method, filteredMethods);
            }
        }

        return method;
    }

};