//,temp,sample_1858.java,2,16,temp,sample_1857.java,2,17
//,3
public class xxx {
public void dummy_method(){
if (unmarshalId != null) {
resolveUnmarshaller(exchange, unmarshalId).process(exchange);
if (exchange.getException() != null) {
throw exchange.getException();
}
}
Class<?> targetModel = endpoint.getCamelContext().getClassResolver().resolveMandatoryClass( endpoint.getConfiguration().getTargetModel());
Message msg = exchange.hasOut() ? exchange.getOut() : exchange.getIn();
String sourceType = endpoint.getConfiguration().getSourceModel();
if (sourceType != null) {


log.info("converting to source model");
}
}

};