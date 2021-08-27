//,temp,AdviceWithTasks.java,488,497,temp,AdviceWithTasks.java,479,484
//,3
public class xxx {
            public void task() throws Exception {
                FromDefinition from = route.getInput();
                LOG.info("AdviceWith replace input from [{}] --> [{}]", from.getEndpointUri(), uri);
                from.setEndpoint(null);
                from.setUri(uri);
            }

};