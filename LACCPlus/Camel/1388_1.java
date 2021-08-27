//,temp,FhirCreateIT.java,68,79,temp,FhirValidateIT.java,56,68
//,3
public class xxx {
    @Test
    public void testCreateStringResourceEncodeXml() throws Exception {
        Patient patient = new Patient().addName(new HumanName().addGiven("Vincent").setFamily("Freeman"));
        String patientString = this.fhirContext.newXmlParser().encodeResourceToString(patient);
        Map<String, Object> headers = new HashMap<>();
        headers.put(ExtraParameters.ENCODE_XML.getHeaderName(), Boolean.TRUE);
        MethodOutcome result = requestBodyAndHeaders("direct://RESOURCE_STRING", patientString, headers);

        LOG.debug("resource: " + result);
        assertNotNull(result, "resource result");
        assertTrue(result.getCreated());
    }

};