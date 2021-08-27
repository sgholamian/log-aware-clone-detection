//,temp,sample_1858.java,2,16,temp,sample_1857.java,2,17
//,3
public class xxx {
public void dummy_method(){
Class<?> targetModel = endpoint.getCamelContext().getClassResolver().resolveMandatoryClass( endpoint.getConfiguration().getTargetModel());
Message msg = exchange.hasOut() ? exchange.getOut() : exchange.getIn();
String sourceType = endpoint.getConfiguration().getSourceModel();
if (sourceType != null) {
Class<?> sourceModel = endpoint.getCamelContext() .getClassResolver().resolveClass(sourceType);
if (sourceModel == null) {
throw new Exception("Unable to load sourceModel class: " + sourceType);
}
msg.setBody(msg.getBody(sourceModel));
}


log.info("mapping to target model");
}

};