//,temp,UnitOfWorkTest.java,54,65,temp,UnitOfWorkTest.java,41,52
//,2
public class xxx {
    @Test
    public void testSuccess() throws Exception {
        sendMessage();

        assertTrue(doneLatch.await(5, TimeUnit.SECONDS), "Exchange did not complete.");
        assertNull(failed, "Should not have failed");
        assertNotNull(completed, "Should have received completed notification");
        assertEquals("bar", foo, "Should have propagated the header inside the Synchronization.onComplete() callback");
        assertNull(baz, "The Synchronization.onFailure() callback should have not been invoked");

        log.info("Received completed: " + completed);
    }

};