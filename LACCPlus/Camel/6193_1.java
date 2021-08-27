//,temp,FhirSearchIT.java,40,50,temp,FhirExtraParametersIT.java,44,58
//,3
public class xxx {
    @Test
    public void testSearchByUrl() throws Exception {
        String url = "Patient?given=Vincent&family=Freeman&_format=json";
        Bundle result = requestBody("direct://SEARCH_BY_URL", url);

        LOG.debug("searchByUrl: " + result);
        assertNotNull(result, "searchByUrl result");
        Patient patient = (Patient) result.getEntry().get(0).getResource();
        assertNotNull(patient);
        assertEquals("Freeman", patient.getName().get(0).getFamily());
    }

};