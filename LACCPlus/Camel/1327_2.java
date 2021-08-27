//,temp,BoxFilesManagerIT.java,234,246,temp,BoxUsersManagerIT.java,206,219
//,3
public class xxx {
    @Test
    public void testGetAllEnterpriseOrExternalUsers() throws Exception {
        final Map<String, Object> headers = new HashMap<>();
        // parameter type is String
        headers.put("CamelBox.filterTerm", null);
        // parameter type is String[]
        headers.put("CamelBox.fields", null);

        @SuppressWarnings("rawtypes")
        final java.util.List result = requestBodyAndHeaders("direct://GETALLENTERPRISEOREXTERNALUSERS", null, headers);

        assertNotNull(result, "getAllEnterpriseOrExternalUsers result");
        LOG.debug("getAllEnterpriseOrExternalUsers: " + result);
    }

};