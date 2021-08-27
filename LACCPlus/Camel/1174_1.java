//,temp,BoxUsersManagerIT.java,89,118,temp,BoxUsersManagerIT.java,66,87
//,3
public class xxx {
    @Test
    public void testCreateAppUser() throws Exception {
        //This test makes sense only with JWT authentication. With standard (OAuth) it will always fail.
        assumeTrue(jwtAuthentication, "Test has to be executed with standard authentication.");

        com.box.sdk.BoxUser result = null;

        try {
            CreateUserParams params = new CreateUserParams();
            params.setSpaceAmount(1073741824); // 1 GB

            final Map<String, Object> headers = new HashMap<>();
            // parameter type is String
            headers.put("CamelBox.name", CAMEL_TEST_CREATE_APP_USER_NAME);
            // parameter type is com.box.sdk.CreateUserParams
            headers.put("CamelBox.params", params);

            result = requestBodyAndHeaders("direct://CREATEAPPUSER", null, headers);

            assertNotNull(result, "createAppUser result");
            LOG.debug("createAppUser: " + result);
        } finally {
            if (result != null) {
                try {
                    result.delete(false, true);
                } catch (Throwable t) {
                }
            }
        }
    }

};