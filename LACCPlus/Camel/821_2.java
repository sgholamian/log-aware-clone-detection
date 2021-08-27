//,temp,FhirDeleteIT.java,68,83,temp,FhirMetaIT.java,90,103
//,3
public class xxx {
    @Test
    public void testGetFromResource() throws Exception {
        final Map<String, Object> headers = new HashMap<>();
        // parameter type is Class
        headers.put("CamelFhir.metaType", Meta.class);
        // parameter type is org.hl7.fhir.instance.model.api.IIdType
        headers.put("CamelFhir.id", this.patient.getIdElement());

        IBaseMetaType result = requestBodyAndHeaders("direct://GET_FROM_RESOURCE", null, headers);

        LOG.debug("getFromResource: " + result);
        assertNotNull(result, "getFromResource result");
        assertEquals(0, result.getTag().size());
    }

};