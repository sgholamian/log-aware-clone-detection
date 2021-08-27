//,temp,FhirDeleteIT.java,85,95,temp,FhirDeleteIT.java,45,54
//,3
public class xxx {
    @Test
    public void testDeleteResource() throws Exception {
        assertTrue(patientExists());
        // using org.hl7.fhir.instance.model.api.IBaseResource message body for single parameter "resource"
        IBaseOperationOutcome result = requestBody("direct://RESOURCE", this.patient);

        LOG.debug("resource: " + result);
        assertNotNull(result, "resource result");
        assertFalse(patientExists());
    }

};