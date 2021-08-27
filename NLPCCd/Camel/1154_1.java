//,temp,sample_2357.java,2,10,temp,sample_487.java,2,15
//,3
public class xxx {
public void process(Exchange exchange) throws Exception {
List<String> params = new ArrayList<String>();
params.add(exchange.getIn().getBody(String.class));
exchange.getOut().setBody(params);
String operation = (String)exchange.getIn().getHeader(CxfConstants.OPERATION_NAME);


log.info("the operation name is");
}

};