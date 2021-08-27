//,temp,FhirOperationIT.java,142,162,temp,BoxFilesManagerIT.java,96,114
//,3
public class xxx {
    @Disabled("Not implemented yet in HAPI FHIR server side, see"
              + " https://github.com/jamesagnew/hapi-fhir/blob/master/hapi-fhir-jpaserver-base/src/main/java/ca/uhn/fhir/jpa/dao/dstu3/FhirResourceDaoMessageHeaderDstu3.java#L33")
    @Test
    public void testProcessMessage() throws Exception {
        final Map<String, Object> headers = new HashMap<>();
        // parameter type is String
        headers.put("CamelFhir.respondToUri", null);
        // parameter type is org.hl7.fhir.instance.model.api.IBaseBundle
        headers.put("CamelFhir.msgBundle", null);
        headers.put("CamelFhir.asynchronous", Boolean.FALSE);
        // parameter type is Class
        headers.put("CamelFhir.responseClass", null);
        // parameter type is java.util.Map
        headers.put("CamelFhir.extraParameters", null);

        final org.hl7.fhir.instance.model.api.IBaseBundle result
                = requestBodyAndHeaders("direct://PROCESS_MESSAGE", null, headers);

        assertNotNull(result, "processMessage result");
        LOG.debug("processMessage: " + result);
    }

};