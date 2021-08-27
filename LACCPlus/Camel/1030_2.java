//,temp,FhirCapabilitiesIT.java,54,65,temp,FhirCapabilitiesIT.java,45,52
//,3
public class xxx {
    @Test
    public void testOfType() throws Exception {
        org.hl7.fhir.instance.model.api.IBaseConformance result = requestBody("direct://OF_TYPE", CapabilityStatement.class);

        LOG.debug("ofType: " + result);
        assertNotNull(result, "ofType result");
        assertEquals(Enumerations.PublicationStatus.ACTIVE, ((CapabilityStatement) result).getStatus());
    }

};