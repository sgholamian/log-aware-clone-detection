//,temp,FhirCapabilitiesIT.java,54,65,temp,FhirCapabilitiesIT.java,45,52
//,3
public class xxx {
    @Test
    public void testEncodeJSON() throws Exception {
        Map<String, Object> headers = new HashMap<>();
        headers.put(ExtraParameters.ENCODE_JSON.getHeaderName(), Boolean.TRUE);

        org.hl7.fhir.instance.model.api.IBaseConformance result
                = requestBodyAndHeaders("direct://OF_TYPE", CapabilityStatement.class, headers);

        LOG.debug("ofType: " + result);
        assertNotNull(result, "ofType result");
        assertEquals(Enumerations.PublicationStatus.ACTIVE, ((CapabilityStatement) result).getStatus());
    }

};