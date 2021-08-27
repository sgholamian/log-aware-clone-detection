//,temp,FhirCreateIT.java,68,79,temp,FhirValidateIT.java,56,68
//,3
public class xxx {
    @Test
    public void testResourceAsString() throws Exception {
        Patient bobbyHebb = new Patient().addName(new HumanName().addGiven("Bobby").setFamily("Hebb"));
        // using org.hl7.fhir.instance.model.api.IBaseResource message body for single parameter "resource"
        MethodOutcome result
                = requestBody("direct://RESOURCE_AS_STRING", this.fhirContext.newXmlParser().encodeResourceToString(bobbyHebb));

        assertNotNull(result, "resource result");
        LOG.debug("resource: " + result);
        assertNotNull(result.getOperationOutcome());
        assertTrue(((OperationOutcome) result.getOperationOutcome()).getText().getDivAsString()
                .contains("No issues detected during validation"));
    }

};