//,temp,SubscriptionGatewayIT.java,63,76,temp,FhirMetaIT.java,113,125
//,3
public class xxx {
    @Test
    public void testGetFromType() throws Exception {
        final Map<String, Object> headers = new HashMap<>();
        // parameter type is Class
        headers.put("CamelFhir.metaType", Meta.class);
        // parameter type is String
        headers.put("CamelFhir.resourceType", "Patient");

        IBaseMetaType result = requestBodyAndHeaders("direct://GET_FROM_TYPE", null, headers);

        LOG.debug("getFromType: " + result);
        assertNotNull(result, "getFromType result");
    }

};