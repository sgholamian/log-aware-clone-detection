//,temp,AuthenticationTest.java,51,58,temp,AbstractAuthorizationTest.java,47,54
//,2
public class xxx {
    private void assertDenied(String userPass, String destination) {
        try {
            assertAllowed(userPass, destination);
            fail("Expected not allowed exception");
        } catch (JMSException expected) {
            LOG.debug("got:" + expected, expected);
        }
    }

};