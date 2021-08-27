//,temp,FhirDeleteIT.java,85,95,temp,FhirDeleteIT.java,45,54
//,3
public class xxx {
    @Test
    public void testDeleteResourceConditionalByUrl() throws Exception {
        assertTrue(patientExists());

        IBaseOperationOutcome result
                = requestBody("direct://RESOURCE_CONDITIONAL_BY_URL", "Patient?given=Vincent&family=Freeman");

        LOG.debug("resourceConditionalByUrl: " + result);
        assertNotNull(result, "resourceConditionalByUrl result");
        assertFalse(patientExists());
    }

};