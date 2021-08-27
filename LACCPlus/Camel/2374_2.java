//,temp,FacebookProducer.java,137,165,temp,AbstractApiProducer.java,135,164
//,3
public class xxx {
    protected ApiMethod findMethod(Exchange exchange, Map<String, Object> properties) {

        ApiMethod method = null;
        final List<ApiMethod> candidates = endpoint.getCandidates();
        if (processInBody(exchange, properties)) {

            // filter candidates based on endpoint and exchange properties
            final Set<String> argNames = properties.keySet();
            final List<ApiMethod> filteredMethods = methodHelper.filterMethods(
                    candidates,
                    ApiMethodHelper.MatchType.SUPER_SET,
                    argNames);

            // get the method to call
            if (filteredMethods.isEmpty()) {
                throw new RuntimeCamelException(
                        String.format("Missing properties for %s, need one or more from %s",
                                endpoint.getMethodName(),
                                methodHelper.getMissingProperties(endpoint.getMethodName(), argNames)));
            } else if (filteredMethods.size() == 1) {
                // found an exact match
                method = filteredMethods.get(0);
            } else {
                method = ApiMethodHelper.getHighestPriorityMethod(filteredMethods);
                log.warn("Calling highest priority operation {} from operations {}", method, filteredMethods);
            }
        }

        return method;
    }

};