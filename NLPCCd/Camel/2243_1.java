//,temp,sample_2430.java,2,10,temp,sample_2429.java,2,10
//,2
public class xxx {
public void dummy_method(){
Hits hits = exchange.getIn().getBody(Hits.class);
try {
printResults(hits);
} catch (Exception e) {
LOG.error(e.getMessage());
exchange.getOut().setBody(null);
}
}

};