//,temp,FhirOperationIT.java,73,98,temp,FhirOperationIT.java,46,71
//,2
public class xxx {
    @Test
    public void testOnInstance() throws Exception {
        final Map<String, Object> headers = new HashMap<>();
        // parameter type is org.hl7.fhir.instance.model.api.IIdType
        headers.put("CamelFhir.id", this.patient.getIdElement());
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

        final Parameters result = requestBodyAndHeaders("direct://ON_INSTANCE", null, headers);

        LOG.debug("onInstance: " + result);
        assertNotNull(result, "onInstance result");
        Bundle bundle = (Bundle) result.getParameter().get(0).getResource();
        assertNotNull(bundle, "onInstance result");
        IdType id = bundle.getEntry().get(0).getResource().getIdElement().toUnqualifiedVersionless();
        assertEquals(patient.getIdElement().toUnqualifiedVersionless(), id);
    }

};