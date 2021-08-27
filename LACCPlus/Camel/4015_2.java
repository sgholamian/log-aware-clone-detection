//,temp,FhirCreateIT.java,56,66,temp,FhirCreateIT.java,45,54
//,3
public class xxx {
    @Test
    public void testCreateResource() throws Exception {
        Patient patient = new Patient().addName(new HumanName().addGiven("Vincent").setFamily("Freeman"));

        MethodOutcome result = requestBody("direct://RESOURCE", patient);

        LOG.debug("resource: " + result);
        assertNotNull(result, "resource result");
        assertTrue(result.getCreated());
    }

};