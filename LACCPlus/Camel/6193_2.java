//,temp,FhirSearchIT.java,40,50,temp,FhirExtraParametersIT.java,44,58
//,3
public class xxx {
    @Test
    public void testEncodeRequestToXml() throws Exception {
        final Map<String, Object> headers = new HashMap<>();
        // encode request to XML
        headers.put(ExtraParameters.ENCODE_XML.getHeaderName(), Boolean.TRUE);
        String url = "Patient?given=Vincent&family=Freeman&_format=json";

        Bundle result = requestBodyAndHeaders("direct://SEARCH_BY_URL", url, headers);

        LOG.debug("searchByUrl: " + result);
        assertNotNull(result, "searchByUrl result");
        Patient patient = (Patient) result.getEntry().get(0).getResource();
        assertNotNull(patient);
        assertEquals("Freeman", patient.getName().get(0).getFamily());
    }

};