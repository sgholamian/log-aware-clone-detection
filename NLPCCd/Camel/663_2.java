//,temp,sample_3782.java,2,10,temp,sample_5875.java,2,13
//,3
public class xxx {
protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
synchronized (endpoints) {
Endpoint endpoint = endpoints.get(uri);
if (endpoint == null) {
endpoint = new TradeExecutorEndpoint(uri, new TradeExecutor());
endpoints.put(uri, (TradeExecutorEndpoint) endpoint);


log.info("created trade executor");
}
}
}

};