//,temp,AdviceWithTasks.java,488,497,temp,AdviceWithTasks.java,479,484
//,3
public class xxx {
    public static AdviceWithTask replaceFrom(final RouteDefinition route, final Endpoint endpoint) {
        return new AdviceWithTask() {
            public void task() throws Exception {
                FromDefinition from = route.getInput();
                LOG.info("AdviceWith replace input from [{}] --> [{}]", from.getEndpointUri(), endpoint.getEndpointUri());
                from.setUri(null);
                from.setEndpoint(endpoint);
            }
        };
    }

};