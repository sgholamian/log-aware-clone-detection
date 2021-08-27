//,temp,FhirDeleteIT.java,68,83,temp,FhirMetaIT.java,90,103
//,3
public class xxx {
    @Test
    public void testDeleteResourceByStringId() throws Exception {
        assertTrue(patientExists());

        Map<String, Object> headers = new HashMap<>();
        // parameter type is String
        headers.put("CamelFhir.type", "Patient");
        // parameter type is String
        headers.put("CamelFhir.stringId", this.patient.getIdElement().getIdPart());

        IBaseOperationOutcome result = requestBodyAndHeaders("direct://RESOURCE_BY_STRING_ID", null, headers);

        LOG.debug("resourceById: " + result);
        assertNotNull(result, "resourceById result");
        assertFalse(patientExists());
    }

};