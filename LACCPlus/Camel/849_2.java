//,temp,DriveFilesIT.java,204,218,temp,FhirMetaIT.java,127,140
//,3
public class xxx {
    @Test
    public void testGetFromTypePreferResponseType() throws Exception {
        final Map<String, Object> headers = new HashMap<>();
        // parameter type is Class
        headers.put("CamelFhir.metaType", Meta.class);
        // parameter type is String
        headers.put("CamelFhir.resourceType", "Patient");
        headers.put(ExtraParameters.PREFER_RESPONSE_TYPE.getHeaderName(), Patient.class);

        Meta result = requestBodyAndHeaders("direct://GET_FROM_TYPE", null, headers);

        LOG.debug("getFromType: " + result);
        assertNotNull(result, "getFromType result");
    }

};