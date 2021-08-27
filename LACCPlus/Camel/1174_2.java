//,temp,BoxUsersManagerIT.java,89,118,temp,BoxUsersManagerIT.java,66,87
//,3
public class xxx {
    @Disabled
    @Test
    public void testAddUserEmailAlias() throws Exception {
        com.box.sdk.EmailAlias result = null;
        try {
            final Map<String, Object> headers = new HashMap<>();
            // parameter type is String
            headers.put("CamelBox.userId", testUser.getID());
            // parameter type is String
            headers.put("CamelBox.email", CAMEL_TEST_USER_EMAIL_ALIAS);
            result = requestBodyAndHeaders("direct://ADDUSEREMAILALIAS", null, headers);
            assertNotNull(result, "addUserEmailAlias result");
            LOG.debug("addUserEmailAlias: " + result);
        } finally {
            if (result != null) {
                try {
                    testUser.deleteEmailAlias(result.getID());
                } catch (Throwable t) {
                }
            }
        }
    }

};