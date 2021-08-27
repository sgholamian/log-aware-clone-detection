//,temp,sample_3286.java,2,11,temp,sample_2365.java,2,14
//,3
public class xxx {
public AbstractJIRAConsumer(JIRAEndpoint endpoint, Processor processor) {
super(endpoint, processor);
this.endpoint = endpoint;
setDelay(endpoint.getDelay());
JerseyJiraRestClientFactory factory;
Registry registry = endpoint.getCamelContext().getRegistry();
Object target = registry.lookupByName("JerseyJiraRestClientFactory");
if (target != null) {


log.info("jerseyjirarestclientfactory found in registry");
}
}

};