//,temp,AbstractAuthorizationTest.java,78,85,temp,AbstractAuthorizationTest.java,26,33
//,3
public class xxx {
    protected void assertDeniedTemp(String userPass) {
        try {
            assertAllowedTemp(userPass);
            fail("Expected not allowed exception");
        } catch (Exception expected) {
            LOG.debug("got:" + expected, expected);
        }
    }

};