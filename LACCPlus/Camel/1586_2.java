//,temp,FhirHistoryIT.java,61,71,temp,FhirHistoryIT.java,45,59
//,3
public class xxx {
    @Test
    public void testOnInstance() throws Exception {
        final Map<String, Object> headers = new HashMap<>();
        headers.put("CamelFhir.id", this.patient.getIdElement());
        // parameter type is Class
        headers.put("CamelFhir.returnType", Bundle.class);
        // parameter type is Integer
        headers.put("CamelFhir.count", 1);

        Bundle result = requestBodyAndHeaders("direct://ON_INSTANCE", null, headers);

        LOG.debug("onInstance: " + result);
        assertNotNull(result, "onInstance result");
        assertEquals(1, result.getEntry().size());
    }

};