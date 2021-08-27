//,temp,FhirOperationIT.java,119,140,temp,BoxFilesManagerIT.java,411,437
//,3
public class xxx {
    @Test
    public void testOnType() throws Exception {
        final Map<String, Object> headers = new HashMap<>();
        // parameter type is Class
        headers.put("CamelFhir.resourceType", Patient.class);
        // parameter type is String
        headers.put("CamelFhir.name", "everything");
        // parameter type is org.hl7.fhir.instance.model.api.IBaseParameters
        headers.put("CamelFhir.parameters", null);
        // parameter type is Class
        headers.put("CamelFhir.outputParameterType", Parameters.class);
        headers.put("CamelFhir.useHttpGet", Boolean.FALSE);
        // parameter type is Class
        headers.put("CamelFhir.returnType", null);
        // parameter type is java.util.Map
        headers.put("CamelFhir.extraParameters", null);

        final org.hl7.fhir.instance.model.api.IBaseResource result = requestBodyAndHeaders("direct://ON_TYPE", null, headers);

        assertNotNull(result, "onType result");
        LOG.debug("onType: " + result);
    }

};