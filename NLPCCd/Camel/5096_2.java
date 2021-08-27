//,temp,sample_3449.java,2,12,temp,sample_7836.java,2,10
//,3
public class xxx {
public void marshal(Exchange exchange, Object graph, OutputStream stream) throws Exception {
ObjectHelper.notNull(graph, "The object to marshal must be provided");
List<Map<String, Object>> data = (List<Map<String, Object>>) graph;
if (data.isEmpty()) {


log.info("no data to marshal as the list is empty");
}
}

};