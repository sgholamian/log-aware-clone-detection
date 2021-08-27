//,temp,FhirHistoryIT.java,61,71,temp,FhirHistoryIT.java,45,59
//,3
public class xxx {
    @Test
    public void testOnServer() throws Exception {
        Map<String, Object> headers = new HashMap<>();
        headers.put("CamelFhir.returnType", Bundle.class);
        headers.put("CamelFhir.count", 1);
        Bundle result = requestBodyAndHeaders("direct://ON_SERVER", null, headers);

        LOG.debug("onServer: " + result);
        assertNotNull(result, "onServer result");
        assertEquals(1, result.getEntry().size());
    }

};