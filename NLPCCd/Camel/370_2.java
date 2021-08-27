//,temp,sample_2475.java,2,11,temp,sample_852.java,2,9
//,3
public class xxx {
public void process(Exchange exchange) {
Object input = exchange.getIn().getBody();
if (input instanceof XOrderResponse) {


log.info("endpoint xorderresponse xml");
}
}

};