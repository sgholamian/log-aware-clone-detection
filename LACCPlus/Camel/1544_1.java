//,temp,FhirHistoryIT.java,73,88,temp,BoxFilesManagerIT.java,116,132
//,3
public class xxx {
    @Test
    public void testOnType() throws Exception {
        final Map<String, Object> headers = new HashMap<>();
        // parameter type is Class
        headers.put("CamelFhir.resourceType", Patient.class);
        // parameter type is Class
        headers.put("CamelFhir.returnType", Bundle.class);
        // parameter type is Integer
        headers.put("CamelFhir.count", 1);

        Bundle result = requestBodyAndHeaders("direct://ON_TYPE", null, headers);

        LOG.debug("onType: " + result);
        assertNotNull(result, "onType result");
        assertEquals(1, result.getEntry().size());
    }

};