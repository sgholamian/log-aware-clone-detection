//,temp,sample_2427.java,2,10,temp,sample_2428.java,2,10
//,3
public class xxx {
public void process(Exchange exchange) throws Exception {
Hits hits = exchange.getIn().getBody(Hits.class);
try {
printResults(hits);
} catch (Exception e) {
LOG.error(e.getMessage());
exchange.getOut().setBody(null);
}
}

};