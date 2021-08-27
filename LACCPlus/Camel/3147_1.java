//,temp,CreateRouteWithNonExistingEndpointTest.java,34,45,temp,SendToNonExistingEndpointTest.java,28,37
//,3
public class xxx {
    @Override
    @BeforeEach
    public void setUp() throws Exception {
        try {
            super.setUp();
            fail("Should have failed to create this route!");
        } catch (Exception e) {
            log.debug("Caught expected exception: " + e, e);
            NoSuchEndpointException nse = assertIsInstanceOf(NoSuchEndpointException.class, e.getCause());
            assertEquals("thisUriDoesNotExist", nse.getUri(), "uri");
        }
    }

};