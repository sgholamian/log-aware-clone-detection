//,temp,sample_3091.java,2,9,temp,sample_3090.java,2,8
//,3
public class xxx {
public void process(Exchange exchange) throws Exception {
String payload = exchange.getIn().getBody(String.class);
String report = SchematronProcessorFactory.newScehamtronEngine(endpoint.getRules()).validate(payload);
String status = getValidationStatus(report);


log.info("schematron validation status");
}

};