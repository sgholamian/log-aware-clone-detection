//,temp,AbstractAuthorizationTest.java,78,85,temp,AbstractAuthorizationTest.java,26,33
//,3
public class xxx {
    protected void assertDeniedWrite(String userPass, String destination) {
        try {
            assertAllowedWrite(userPass, destination);
            fail("Expected not allowed exception");
        } catch (JMSException expected) {
            LOG.debug("got:" + expected, expected);
        }
    }

};